import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v126.network.Network;
import org.openqa.selenium.devtools.v126.network.model.Request;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

import java.util.Optional;

public class CDPNetworkExample {

    @Test
    public void CDPDemo(){
        // Initialize WebDriverManager to manage the ChromeDriver binary
        WebDriverManager.chromedriver().setup();

        // ChromeOptions to configure various browser settings
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");

        // Instantiate WebDriver
        WebDriver driver = new ChromeDriver(options);

        // Connect to Chrome DevTools
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        // Enable Network domain in DevTools
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Setting up request interception to filter requests by IP range (126.x.x.x)
        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            String url = req.getUrl();

            System.out.println(req);
            System.out.println(url);

        });

        // Navigating to a webpage (for testing purposes)
        driver.get("https://github.com/");

        // Your logic goes here

        // Close the browser after testing
        driver.quit();
    }
}
