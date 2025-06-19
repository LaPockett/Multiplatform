const { remote } = require('webdriverio')

async function runTestLogout(driver) {
    try {
        await driver.pause(1500);
        const profileScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]')
        const buttonLogout = await driver.$('//android.widget.TextView[@text="Cerrar sesión"]')
        await profileScreen.waitForDisplayed({timeout: 5000});
        profileScreen.click();
        await buttonLogout.waitForDisplayed({timeout: 5000});
        buttonLogout.click();
        await driver.pause(2000);
        const buttonLoginScreen = await driver.$('//android.widget.Button')

        if (await buttonLoginScreen.isDisplayed()) {
            console.log('Test: Pantalla de login visible OK');
        } else {
            throw new Error('La pantalla de login no es visible');
        }
        await driver.pause(2000);

        console.log("Test completado con éxito. LOG OUT");
    } catch (error) {
        throw error;

    }
}
module.exports = { runTestLogout };
//runTestLogin().catch(console.error)
