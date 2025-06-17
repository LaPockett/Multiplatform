const { remote } = require('webdriverio');
const emptyLogin = require('./testEmptyLogin');
const unsuccessfulLogin = require('./testUnsuccessfulLogin');
const successfulLogin = require('./testSuccessfulLogin');
const menuDrawer = require('./testMenuDrawer');

const capabilities = {
    platformName: 'Android',
    'appium:automationName': 'UiAutomator2',
    'appium:deviceName': 'Android',
    'appium:appPackage': 'com.dian.prueba',
    'appium:appActivity': 'com.dian.prueba.view.MainActivity',
    'appium:autoGrantPermissions': true,
    'appium:noReset': false,
    'appium:uiautomator2ServerLaunchTimeout': 60000,
    'appium:adbExecTimeout': 120000,
    'appium:newCommandTimeout': 300
};

async function runAllTests() {
    const driver = await remote({
        hostname: 'localhost',
        port: 4723,
        capabilities: capabilities
    });

    try {
        await driver.pause(2000);

        // 1. Test login vacío
        await emptyLogin.runTestLogin(driver);
        await driver.pause(1000);
        await driver.terminateApp('com.dian.prueba');
        await driver.activateApp('com.dian.prueba');
        //2. Test login no válido
        await unsuccessfulLogin.runTestLogin(driver);
        await driver.pause(1000);
        await driver.terminateApp('com.dian.prueba');
        await driver.activateApp('com.dian.prueba');
        //Los comento porque si no da error en los actions porque
        //se usa un emulador y ciertos elementos no son lo mismo que mi
        // dispositivo real
        // 3. Test login válido
        //await driver.pause(1000);
        //await successfulLogin.runTestLogin(driver);

        // 4. Test menú drawer (swipe)
        //await driver.pause(2000);
        //await menuDrawer.runTestLogin(driver);
        //await driver.pause(1500);

    } catch (error) {
        console.error('Error en un test :', error);
        if (driver) {
            const screenshot = await driver.takeScreenshot();
            require('fs').writeFileSync('screenshot_error.png', screenshot, 'base64');
        }
        throw error;
    } finally {
        if (driver){
            try {
                await driver.deleteSession();
                console.log('Sesión eliminada con éxito  allTest');
            } catch (error) {
                console.error('Error al eliminar la sesión  allTest:', error);
            }
        }
    }
}

runAllTests().catch(console.error);
