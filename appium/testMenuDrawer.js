const { remote } = require('webdriverio')

async function runTestLogin(driver) {
    try {
        await driver.pause(1500);
        const cartScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View[3]')
        await cartScreen.waitForDisplayed({timeout: 10000, interval: 1000});
        cartScreen.click();
        await driver.pause(2000);
        const buttonMenuDrawer = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View');
        await buttonMenuDrawer.waitForDisplayed({timeout: 10000, interval: 1000});
        buttonMenuDrawer.click();
        await driver.pause(2000);

        await driver.execute('mobile: swipeGesture', {
          left: 0, top: 0, width: 1000, height: 2000,
          direction: 'left',
          percent: 0.75
        });
        console.log("Test completado con éxito. MENUDRAWER");
    } catch (error) {
        throw error;

    }
}
module.exports = { runTestLogin };
//runTestLogin().catch(console.error)
