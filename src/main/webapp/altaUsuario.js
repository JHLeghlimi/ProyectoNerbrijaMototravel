
function validarFormulario(){

    let nombre = document.getElementById('nombre').value;
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;

        let ok = true;
    if (nombre == "") {
    	ok = false;
    	document.getElementById('nombre').style.background = "red";
    }
    if (username == "") {
    	ok = false;
    	document.getElementById('username').style.background = "red";
    }
    
    if (email == "") {
    	ok = false;
    	document.getElementById('email').style.background = "red";
    }

    if(ok == true) {
        document.getElementById('altaUsuario').submit();
    }
}