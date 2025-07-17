const { remote } = require('webdriverio')

const capabilities = {
    platformName: 'iOS',
    'appium:automationName': 'XCUITest',
    'appium:deviceName': 'iPhone',
    'appium:platformVersion': '18.5',
    'appium:app': 'org.example.project.KotlinProjectPrueba',
    'appium:udid': 'AEB7BBD1-B10A-484A-95A0-C86EE3D72C3F',
    'appium:autoAcceptAlerts': true,
    'appium:clearSystemFiles': true,
    'appium:autoGrantPermissions': true,
    'appium:showXcodeLog': true,
    'appium:noReset': false,
    'appium:fullReset': false
};
const wdOpts = {
    hostname: process.env.APPIUM_HOST || 'localhost',
    port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
    connectionRetryTimeout: 120000,
    connectionRetryCount: 3,
    logLevel: 'info',
    capabilities,
};

async function runTestLogin() {
    let driver;

    try {
        driver = await remote(wdOpts);
        await driver.activateApp('org.example.project.KotlinProjectPrueba');
        await driver.execute('mobile: clearApp', {
                bundleId: 'org.example.project.KotlinProjectPrueba'
        });
        await driver.pause(1000);

        const emailField = await driver.$('//XCUIElementTypeApplication[@name="KotlinProjectPrueba"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextView[1]');
        await emailField.setValue('diandev@example.com');
        let emailValue = await emailField.getText();

        const passwordField = await driver.$('//XCUIElementTypeApplication[@name="KotlinProjectPrueba"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextView[2]');
        await passwordField.setValue('1234567');
        let passwordValue = await passwordField.getText();

        const loginButton = await driver.$('//XCUIElementTypeButton[@name="Login"]');
        await loginButton.click();
        await driver.pause(2000);

        const alertUpdateButton = await driver.$('//XCUIElementTypeButton[@name="updateButton"]');
        if (await alertUpdateButton.isDisplayed()){
            console.log('Test: Alerta de actualizacion visible OK');
            alertUpdateButton.click();
            await driver.pause(3000);

            await driver.activateApp('org.example.project.KotlinProjectPrueba');
            await driver.pause(3000);

            const exploreScreen = await driver.$('//XCUIElementTypeButton[@name="Explore"]');
            await exploreScreen.waitForDisplayed({timeout: 5000});
            exploreScreen.click();
            await driver.pause(5000)

        }
    } catch (error) {
        console.error('Error en el test:', error);
        if (driver) {
            try {
                const screenshot = await driver.takeScreenshot();
                require('fs').writeFileSync('screenshot_error.png', screenshot, 'base64');
                console.log('Captura de pantalla guardada en screenshot_error.png');
            } catch (screenshotError) {
                console.error('Error al guardar la captura de pantalla:', screenshotError);
            }
        }

        throw error;

    } finally {
        if (driver){
            try {
                await driver.terminateApp('org.example.project.KotlinProjectPrueba');
                await driver.deleteSession();
                console.log('Sesión eliminada con éxito');
            } catch (error) {
                console.error('Error al eliminar la sesión:', error);
            }
        }
    }
}

runTestLogin().catch(console.error);