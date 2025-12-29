CREATE OR REPLACE TRIGGER trg_user_status_log
AFTER UPDATE OR DELETE ON efin_user
FOR EACH ROW
BEGIN
   -- User becomes inactive (1 → 0)
   IF UPDATING
      AND :OLD.efin_user_active_flag = 1
      AND :NEW.efin_user_active_flag = 0
   THEN
      INSERT INTO efin_user_status_log
      VALUES (
         :OLD.efin_user_nbr,
         :OLD.efin_user_active_flag,
         :NEW.efin_user_active_flag,
         'INACTIVATED',
         SYSDATE
      );
   END IF;

   -- User is deleted
   IF DELETING THEN
      INSERT INTO efin_user_status_log
      VALUES (
         :OLD.efin_user_nbr,
         :OLD.efin_user_active_flag,
         NULL,
         'DELETED',
         SYSDATE
      );
   END IF;
END;
/
