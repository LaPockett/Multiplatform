const { remote } = require('webdriverio')

const capabilities = {
    platformName: 'Android',
    'appium:automationName': 'UiAutomator2',
    'appium:deviceName': 'Android',
    'appium:udid': '1e27529b',
    'appium:appPackage': 'com.dian.prueba',
    'appium:appActivity': 'com.dian.prueba.view.MainActivity',
    'appium:autoGrantPermissions': true
};
const wdOpts = {
    hostname: process.env.APPIUM_HOST || 'localhost',
    port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
    logLevel: 'info',
    capabilities,
};

async function runTestLogin() {
    const driver = await remote(wdOpts);
    try {
        await driver.pause(1000);

        const emailField = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]');
        await emailField.setValue('usuario@correo.com');
        let emailValue = await emailField.getText();

        const passwordField = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]');
        await passwordField.setValue('1234567');
        let passwordValue = await passwordField.getText();

        const loginButton = await driver.$('//android.widget.Button');
        await loginButton.click();
        await driver.pause(3000);

        // Verificar que se ha iniciado sesión correctamente

        console.log('Test: email y contraseña válidos OK');
        const alertUpdate = await driver.$('//android.view.ViewGroup/android.view.View/android.view.View/android.view.View')
        if (await alertUpdate.isDisplayed()) {
            console.log('Test: Alerta de actualización visible OK');
            const updateButton = await driver.$('//android.widget.Button')
            updateButton.click();
            await driver.pause(3000);

            const app = await driver.$('//android.widget.FrameLayout[@content-desc="Multiplatform,Desbloqueado"]')
            const buttonMenu = await driver.$('(//android.view.View[@resource-id="com.miui.home:id/task_view_thumbnail"])[3]')
            buttonMenu.click();
            await driver.pause(3000);
            app.click();
            await driver.pause(3000);
            const mainScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]')
            if (await mainScreen.isDisplayed()) {
                console.log('Test: Pantalla principal visible OK');
            } else {
                throw new Error('La pantalla principal no es visible');
            }
        } else {
            throw new Error('La alerta de actualización no es visible');
        }
        
    } catch (error) {
        console.error('Error en el test:', error);
        const screenshot = await driver.takeScreenshot();
        require('fs').writeFileSync('screenshot_error.png', screenshot, 'base64');
        throw error;

    } finally {
        await driver.deleteSession();
    }
}

runTestLogin().catch(console.error);
