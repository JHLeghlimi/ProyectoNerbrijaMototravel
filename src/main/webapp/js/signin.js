
/**
 * Función para obtener el valor de un parámetro en el GET. Una expresión regular.
 */

function llamada(iduser, op) {
            fetch('GestionUsuarios?iduser='+iduser+"&op="+op) 
            .then(response => response.json()) 
            .then(data => pintarFormulario(data))
        }

function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

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
        document.getElementById('signin').submit();
    }
}

const btn = document.getElementById('btn');
btn.addEventListener('click', validarFormulario);

function pintarFormulario(datos) {
	
	document.getElementById("iduser").value = datos.iduser;
	document.getElementById("nombre").value = datos.nombre;
	document.getElementById("username").value = datos.username;
	document.getElementById("email").value = datos.email;
	document.getElementById("permiso").value = datos.permiso;
	
}

window.onload = function(){
	
	let iduser = getParameterByName("iduser");
	let op = getParameterByName("op");
	
	llamada(iduser,op);
	
}







