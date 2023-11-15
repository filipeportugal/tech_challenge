import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCarSearchValidation {

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setUp() {
        val browserChoice = System.getProperty("BROWSER")

        driver = when (browserChoice) {
            "firefox" -> firefoxDriver()
            "chrome" -> chromeDriver()

            else -> {
                println("Invalid browser name")
                throw IllegalArgumentException("Invalid browser name")
            }
        }

        wait = WebDriverWait(driver, Duration.ofSeconds(20))
    }

    @Test
    fun `Input wrong data`() {
        driver.get("https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo?")
        acceptCookies()
        pickNewSouthWales()
        setPostCode()
        defineAsPrivate()
        acceptContinueButton()
        filterVehiclesByPreOwned()
        selectColour()
        orderByPrice()
        selectHighestPrice()
        extractVinAndYear()
        enquireNow()
    }

    @AfterAll
    fun `Close browser`() {
        driver.close()
    }

    private fun acceptCookies(){
        wait.until{ driver.findElement(By.cssSelector("#app > div.dcp-shop > " +
                "cmm-cookie-banner")).shadowRoot }
        val cookieOverlay = driver.findElement(By.cssSelector("#app > div.dcp-shop > " +
                "cmm-cookie-banner")).shadowRoot
        wait.until{ cookieOverlay.findElement(By.cssSelector("div > div > " +
                "div.cmm-cookie-banner__content > cmm-buttons-wrapper > div > div > " +
                "wb7-button.button.button--accept-all.wb-button.hydrated")).isDisplayed }
        Thread.sleep(500)
        cookieOverlay.findElement(By.cssSelector("div > div > div.cmm-cookie-banner__content > cmm-buttons-wrapper > " +
                "div > div > wb7-button.button.button--accept-all.wb-button.hydrated")).click()
    }

    private fun pickNewSouthWales(){
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div/div[4]/div[1]/div/div[1]/div/" +
                "wb-select-control/wb-select/select/option[4]")).click()
    }
    private fun setPostCode(){
        val postCode = driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div/" +
                "div[4]/div[1]/div/div[1]/div/wb-input-control/wb-input/input"))
        wait.until { postCode.isEnabled }
        postCode.sendKeys("2700")
    }

    private fun defineAsPrivate(){
        val privateToggle = driver.findElement(By.cssSelector("#app > div.dcp-shop > header > " +
                "div > div:nth-child(4) > div.wb-modal-dialog__wrapper.wb-modal-dialog-wrapper > div > " +
                "div.wb-modal-dialog-container__content.wb-modal-dialog-content > div > div > div > " +
                "wb-radio-control:nth-child(1) > label > div"))
        wait.until { privateToggle.isEnabled }
        privateToggle.click()
    }
    private fun acceptContinueButton(){
        val continueButton = driver.findElement(By.cssSelector("#app > div.dcp-shop > header > div > " +
                "div:nth-child(4) > div.wb-modal-dialog__wrapper.wb-modal-dialog-wrapper > div > " +
                "div.wb-modal-dialog-container__footer.wb-modal-dialog-footer > button"))
        wait.until { continueButton.isEnabled }
        continueButton.click()
    }

    private fun filterVehiclesByPreOwned(){
        val hiddenFilter = driver.findElement(By.cssSelector("#app > div.dcp-shop > main > " +
                "div.dcp-shop__container > div.dcp-cars-srp > div.wrapper > div.sidebar > span > svg"))
        wait.until { hiddenFilter.isDisplayed && hiddenFilter.isEnabled }
        val filterButton = driver.findElement(By.cssSelector("#app > div.dcp-shop > main > " +
                "div.dcp-shop__container > div.dcp-cars-srp > div.wrapper > div.sidebar > span"))
        wait.until { filterButton.isEnabled }
        filterButton.click()
        val preOwned = driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/" +
                "div[1]/div[2]/div[1]/div/div/div[1]/wb-tabs/wb-tab-bar/wb-tab[1]/button"))
        wait.until { preOwned.isDisplayed && preOwned.isEnabled }
        preOwned.click()
            }

    private fun selectColour(){
        wait.until { driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/" +
                "div[1]/div/div/div[1]/div[4]/div[7]/div/div[1]")).isDisplayed}
        Thread.sleep(500)
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/div[1]/div/div" +
                "/div[1]/div[4]/div[7]/div/div[1]")).click()
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/div[1]/div/div/" +
                "div[1]/div[4]/div[7]/div/div[2]/div/div/a")).click()
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/div[1]/div/div/" +
                "div[1]/div[4]/div[7]/div/div[2]/div/div/ul/li[1]/a")).click()
    }

    private fun orderByPrice(){
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/div[2]/div[2]/" +
                "wb-select-control/wb-select/select/option[6]")).click()
    }

    private fun selectHighestPrice(){
        Thread.sleep(2000)
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div[2]/div[1]/div[2]/div[2]/" +
                "div[3]/div[1]/div/a/div/wb-icon")).click()
    }

    private fun extractVinAndYear(){
        wait.until { driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]" +
                "/div[4]/div/div/div/div/div/ul/li[12]")).isDisplayed }
        val vin = driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/" +
                "div[4]/div/div/div/div/div/ul/li[12]"))
        val year = driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/" +
                "div[4]/div/div/div/div/div/ul/li[4]"))
        File("src/test/kotlin/Task2_details_vin_year.txt").writeText("${vin.text}\n${year.text}")
    }

    private fun enquireNow(){
        val enquireButton = driver.findElement(By.cssSelector("#app > div.dcp-shop > " +
                "main > div > div.dcp-pdp > div.dcp-pdp__teaser > div > div.dcp-pdp-teaser__buy-box > " +
                "div > button"))
        wait.until { enquireButton.isDisplayed}
        enquireButton.click()
        wait.until { driver.findElement(By.cssSelector("#app > div.dcp-shop > main > div > div.dcp-pdp > " +
                "div.dcp-rfq-container > div > div.wb-modal-info__wrapper.wb-modal-info-wrapper > div")).isDisplayed }
        Thread.sleep(2000)

        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[1]/wb-input-control/" +
                "wb-input/input")).sendKeys("John")
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[2]/wb-input-control/" +
                "wb-input/input")).sendKeys("Doe")
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[3]/wb-input-control/" +
                "wb-input/input")).sendKeys("john.doe@.com")
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[4]/wb-input-control/" +
                "wb-input/input")).sendKeys("0444444444")
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[5]/wb-input-control/" +
                "wb-input/input")).sendKeys("2700")
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[1]/form/div/div[8]/div/div/wb-checkbox-control/label/wb-icon")).click()
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/div[1]/div[7]/div/div[1]/div/" +
                "div[2]/div/div/div/div/div[3]/button")).click()

        val errorMessage = driver.findElement(By.xpath("/html/body/div[1]/div[1]/main/div/" +
                "div[1]/div[7]/div/div[1]/div/div[2]/div/div/div/div/div[3]/p"))
        wait.until { errorMessage.isDisplayed}
        assert(errorMessage.text.contains("An error has occurred."))
    }

    private fun chromeDriver(): ChromeDriver{
        val chromeOptions = ChromeOptions().addArguments("--start-maximized",
            "--headless",
            "--window-size=1920,1080")
        return ChromeDriver(chromeOptions)
    }

    private fun firefoxDriver(): FirefoxDriver{
        val firefoxOptions = FirefoxOptions().addArguments("--start-maximized",
            "--headless",
            "--window-size=1920,1080")
        return FirefoxDriver(firefoxOptions)
    }
}


