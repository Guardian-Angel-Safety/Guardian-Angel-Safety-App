const axios = require('axios');

var gas_url = "https://8080-dot-7075758-dot-devshell.appspot.com/";
axios.get(gas_url).then((response) => {console.log(response);});