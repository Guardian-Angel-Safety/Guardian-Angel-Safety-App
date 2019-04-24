var gas_url = "https://gas-device.appspot.com/";
function setDemoState(state)
{
    axios.post(gas_url, {demo_state: state}).then((response) => {console.log(response);});
}