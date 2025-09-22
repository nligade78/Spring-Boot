package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}



import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public JSONObject getMetadataOfDocument(String bucketID) throws Exception {
    logger.debug("getMetadataOfDocument called with bucketID='{}'", bucketID);
    try {
        HierarchicalAssetService hierarchialAsset = geneva.hierarchicalAssetService(applicationProperties.getTopicParam());

        // Use explicit charset for bytes
        byte[] bucketBytes = bucketID.getBytes(StandardCharsets.UTF_8);
        logger.debug("Calling getIterator with bucket bytes length = {}", bucketBytes.length);

        Iterator iter = hierarchialAsset.getIterator(bucketBytes, null, 1, HAType.BUCKET);
        if (iter == null) {
            String msg = "Iterator returned null for bucketId=" + bucketID;
            logger.warn(msg);
            throw new RuntimeException(msg);
        }

        int itemCount = 0;
        while (iter.next()) {
            itemCount++;
            IterItem item = hierarchialAsset.getIterItem();
            byte[] metadata = item.getMetaData();
            logger.debug("Iter item #{} metadata byte[] = {}", itemCount, metadata == null ? "null" : metadata.length);

            if (metadata != null && metadata.length > 0) {
                String rawMetadata = new String(metadata, StandardCharsets.UTF_8);
                logger.info("Raw metadata (bucketId={}): {}", bucketID, rawMetadata);

                JSONObject jsonObject = (JSONObject) new JSONParser().parse(rawMetadata);
                logger.info("Parsed jsonObject = {}", jsonObject.toJSONString());
                return jsonObject;
            } else {
                logger.debug("Metadata was null/empty for iter item #{} for bucketId={}", itemCount, bucketID);
            }
        }

        String msg = "No metadata found in iterator for bucketId=" + bucketID + " (items iterated=" + itemCount + ")";
        logger.warn(msg);
        throw new RuntimeException(msg);

    } catch (SocketTimeoutException ste) {
        hubbleEventHelper.captureCount(HubbleEventName.GENEVA_INTERFACE_DOWN.getValue(), 1L);
        throw new Exception("Geneva timed out while getting metadata for bucketId=" + bucketID, ste);
    } catch (Exception ex) {
        logger.error("Failed to get metadata for bucketId=" + bucketID, ex);
        throw new Exception("Failed to get metadata for bucketId=" + bucketID + " -> " + ex.getMessage(), ex);
    }
}
