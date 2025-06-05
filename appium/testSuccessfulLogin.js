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

            await driver.pressKeyCode(KEYCODE_MULTITAREA);
            await driver.pause(1000);

            const app = await driver.$('//android.widget.FrameLayout[@content-desc="Multiplatform,Desbloqueado"]')
            await driver.pause(1500);
            app.click();
            await driver.pause(1500);
            const mainScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]')

            if (await mainScreen.isDisplayed()) {
                console.log('Test: Pantalla principal visible OK');
                const profileScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[2]')
                const cartScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[3]')
                const exploreScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View[4]')
                const buttonBack = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View[3]/android.widget.Button')
                await profileScreen.waitForDisplayed({timeout: 5000});
                profileScreen.click();
                await buttonBack.waitForDisplayed({timeout: 5000});
                buttonBack.click();

                await cartScreen.waitForDisplayed({timeout: 5000});
                cartScreen.click();
                await exploreScreen.waitForDisplayed({timeout: 5000});
                exploreScreen.click();
                await driver.pause(2000);

                console.log("Test completado con éxito. LOGIN VÁLIDO");
            } else {
                throw new Error('La pantalla principal no es visible');
            }
        } else {
            throw new Error('La alerta de actualización no es visible');
        }
        
    } catch (error) {
        throw error;

    }
}

module.exports = { runTestLogin };
