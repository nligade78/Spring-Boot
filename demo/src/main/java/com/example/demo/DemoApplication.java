package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}




public PRRequestForm fetchPRJsonForSave(String massPRId) throws Exception {
    Logger.info("massPRId = {}", massPRId);

    MassPRMonitor massPRMonitor = new MassPRMonitor();
    massPRMonitor.setMassPRId(massPRId);

    int retryCount = 0;
    while (retryCount < 3) {
        massPRMonitor = massPRMonitorDAO.get(massPRMonitor);
        if (massPRMonitor != null) break;

        retryCount++;
        Thread.sleep(1000); // wait 1 second before retry
    }

    if (massPRMonitor != null && massPRMonitor.getFormNumber() == null) {
        return new ObjectMapper()
            .readValue(clobToString(massPRMonitor.getJsonData()), PRRequestForm.class);
    }

    return null;
}
