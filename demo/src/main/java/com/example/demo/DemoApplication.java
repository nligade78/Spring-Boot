 // UPDATED: validatePRLineItemData now supports optional BA & Bill-To validation when present on line-item sheet
private boolean validatePRLineItemData(Workbook importedExcelData, PRFormError formError, Long prSequenceNumber) throws Exception {
    boolean lineItemHasError = false;

    FormulaEvaluator formulaEval = importedExcelData.getCreationHelper().createFormulaEvaluator();
    DataFormatter objDefaultFormat = new DataFormatter();

    // ADDED: get the line item sheet index (sheet 1 in your templates)
    Sheet lineItemDataSheet = importedExcelData.getSheetAt(1); // ADDED
    if (lineItemDataSheet == null) {
        return false;
    }

    // ADDED: build header index map from the line-item sheet header row
    Map<String, Integer> lineHeaderIndex = buildHeaderIndex(lineItemDataSheet, BulkExportConstants.HEADER_ROW); // ADDED
    Integer baColIdx = null;
    Integer billToColIdx = null;
    // possible header names — update to actual header text used in templates if different
    if (lineHeaderIndex.containsKey("business area")) { baColIdx = lineHeaderIndex.get("business area"); } // ADDED
    else if (lineHeaderIndex.containsKey("ba")) { baColIdx = lineHeaderIndex.get("ba"); } // ADDED fallback
    if (lineHeaderIndex.containsKey("bill to")) { billToColIdx = lineHeaderIndex.get("bill to"); } // ADDED
    else if (lineHeaderIndex.containsKey("billto")) { billToColIdx = lineHeaderIndex.get("billto"); } // ADDED fallback

    // prepare list to collect PR line errors
    List<PRLineError> prLineErrors = new ArrayList<>();

    // your existing variables and for loop structure
    int startRow = BulkExportConstants.DATA_ROW; // typically where line items start
    for (int rowIndex = startRow; rowIndex <= lineItemDataSheet.getLastRowNum(); rowIndex++) {
        Row rowWithData = lineItemDataSheet.getRow(rowIndex);
        if (rowWithData == null || isRowBlank(rowWithData)) { // you may already have isRowBlank helper
            continue;
        }

        // get pr sequence number for this line (existing helper from your code)
        Long currentLinePrSeq = getLinePRSeqNumber(rowWithData, formulaEval); // assume exists; else use your function
        if (currentLinePrSeq == null) {
            continue;
        }
        // only validate lines that belong to the requested PR sequence, matching existing logic
        if (!currentLinePrSeq.equals(prSequenceNumber)) {
            continue;
        }

        boolean thisLineHasError = false;
        int columnIndex = 0;
        Integer prLineNumber = null;
        Iterator<Cell> cellIterator = rowWithData.cellIterator();

        while (cellIterator.hasNext() && columnIndex < BulkExportConstants.MAX_COLUMNS_LINE) {
            Cell currentCell = cellIterator.next();

            // existing switch on PRLineExcelColumn enum
            PRLineExcelColumn colEnum = PRLineExcelColumn.getEnumFromIndex(columnIndex);

            switch (colEnum) {
                case FIELD:
                    // existing code to set prLineNumber
                    prLineNumber = getLineNumber(currentCell); // assume your helper
                    break;

                case PR_SEQ_NUMBER:
                    // existing validation for pr seq number if missing -> error
                    if (prLineNumber == null) {
                        PRLineError lineError = new PRLineError(prLineNumber,
                                PRLineExcelColumn.getEnumFromIndex(columnIndex).getColumnName(),
                                ErrorLogs.ERROR_PR_SEQUENCE_NUMBER_NOT_PROVIDED);
                        prLineErrors.add(lineError);
                        thisLineHasError = true;
                    }
                    break;

                case QUANTITY:
                    formulaEval.evaluate(currentCell);
                    String quantity = objDefaultFormat.formatCellValue(currentCell, formulaEval);
                    if (StringUtils.isNotEmpty(quantity) && !NumberUtils.isNumber(quantity)) {
                        PRLineError lineError = new PRLineError(prLineNumber,
                                PRLineExcelColumn.getEnumFromIndex(columnIndex).getColumnName(),
                                ErrorLogs.ERROR_QUANTITY_NOT_VALID);
                        prLineErrors.add(lineError);
                        thisLineHasError = true;
                    }
                    break;

                // ... include all your existing column cases here unchanged ...
                // PRICE_PER_UNIT, DELIVERY_DATE, COST_CENTER_NUMBER, GL_ACCOUNT, NOTE_TO_PURCHASING, etc.

                // ADDED: Business Area validation when BA exists in line-item sheet
                default:
                    // If the column index is the BA or Bill-To columns we discovered above,
                    // validate those values. We do not require them if column not present.
                    break;
            } // end switch

            columnIndex++;
        } // end cell iteration

        // ADDED: AFTER iterating columns, validate BA / BillTo by using direct header indices (if present)
        if (baColIdx != null) {
            String baValue = getCellString(rowWithData, baColIdx); // ADDED
            if (StringUtils.isNotBlank(baValue)) {
                // validate BA value - use your reference/master check call
                if (!referenceDataService.isValidBusinessArea(baValue)) { // ADDED
                    PRLineError lineError = new PRLineError(prLineNumber,
                            "Business Area", // display name
                            ErrorLogs.ERROR_BUSINESS_AREA_NOT_VALID);
                    prLineErrors.add(lineError);
                    thisLineHasError = true;
                }
            } else {
                // If BA column exists in template and you want BA mandatory for line items,
                // uncomment the following block to make it mandatory:
                /*
                PRLineError lineError = new PRLineError(prLineNumber,
                        "Business Area",
                        ErrorLogs.ERROR_BUSINESS_AREA_NOT_PROVIDED);
                prLineErrors.add(lineError);
                thisLineHasError = true;
                */
            }
        }

        if (billToColIdx != null) {
            String billToValue = getCellString(rowWithData, billToColIdx); // ADDED
            if (StringUtils.isNotBlank(billToValue)) {
                // validate Bill-To value
                if (!referenceDataService.isValidBillTo(billToValue)) { // ADDED
                    PRLineError lineError = new PRLineError(prLineNumber,
                            "Bill To",
                            ErrorLogs.ERROR_BILLTO_NOT_VALID);
                    prLineErrors.add(lineError);
                    thisLineHasError = true;
                }
            } else {
                // If Bill-To column exists and must be mandatory, add similar mandatory block here
            }
        }

        // if any error for this line, set flag
        if (thisLineHasError) {
            lineItemHasError = true;
        }
    } // end for rows

    if (lineItemHasError) {
        formError.setPrLineErrors(prLineErrors); // keep same field name you use
    }

    return lineItemHasError;
}
