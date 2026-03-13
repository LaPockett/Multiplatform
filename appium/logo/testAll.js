const { remote } = require('webdriverio');
const accessToLogo = require('./testAccessToLogo');
const bottomNavigation = require('./testBottomNavigation');
const selectProduct = require('./testSelectProduct');

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
        // 1. Splash Screens and access to closet
        await accessToLogo.runTestAccessToLogo(driver);
        await driver.pause(1000);
        // 2. Bottom Navigation
        await bottomNavigation.runTestBottomNavigation(driver);
        await driver.pause(1000);
        // 3. Select Product
        await selectProduct.runTestSelectProduct(driver);
        await driver.pause(1000);
        // TODO: 4. Zoom Image
    } catch (error) {
        console.error('⚠️ Algo ha fallado\n', error);
        if (driver) {
            const screenshot = await driver.takeScreenshot();
            require('fs').writeFileSync('screenshot_error_logo.png', screenshot, 'base64');
        }
        throw error;
    } finally {
        if (driver){
            try {
                await driver.deleteSession();
                console.log('✅ 🔨 Sesión eliminada con éxito allTest ✅ 🔨');
            } catch (error) {
                console.error('❌ ⚠️ Error al eliminar la sesión allTest:', error);
            }
        }
    }
}

runAllTests().catch(console.error);
