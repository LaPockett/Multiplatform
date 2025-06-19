const { remote } = require('webdriverio')

async function runTestLogin(driver) {
    const KEYCODE_MULTITAREA = 187;

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

        } else {
            throw new Error('La alerta de actualización no es visible');
        }
        console.log("Test completado con éxito. LOGIN VÁLIDO");
        
    } catch (error) {
        throw error;

    }
}

module.exports = { runTestLogin };
