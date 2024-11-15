import Lucida from "lucida";
// @ts-ignore
import Deezer from "lucida/streamers/deezer/main.js";

const lucida = new Lucida({
    modules: {
        deezer: new Deezer({}),
    },
    logins: {
        deezer: {
            username: process.env.DEEZER_LOGIN,
            password: process.env.DEEZER_PASSWORD,
        }
    }
})

async function connectLucida() {
    await lucida.login()
}

async function disconnectLucida() {
    await lucida.disconnect()
}

export {
    lucida,
    connectLucida,
    disconnectLucida
}