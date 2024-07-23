package steps;

import org.openqa.selenium.WebDriver;

public class Steps {

    private final WebDriver webDriver;

    public Steps(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Steps goToUrl(String URL) {
        webDriver.get(URL);
        return this;
    }

    public Steps windowMaximize(){
        webDriver.manage().window().maximize();
        return this;
    }
}
