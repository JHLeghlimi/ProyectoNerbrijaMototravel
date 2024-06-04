/**
 * 
 */
function llamada(idruta, op) {
            fetch('GestionRutas?idruta='+idruta+"&op="+op) 
            .then(response => response.json()) 
            .then(data => pintarFormulario(data))
        }
        
/**
 * Función para obtener el valor de un parámetro en el GET
 */
function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

const btn = document.getElementById('btn-guardar');
btn.addEventListener('click', validarFormulario);

function validarFormulario() {

	let titulo = document.getElementById('titulo').value;
	let estilo = document.getElementById('estilo').value;
	let descripcion = document.getElementById('descripcion').value;
	let fecha = document.getElementById('fecha').value;
	let foto = document.getElementById('foto').value;

	let ok = true;
	if (titulo == "") {
		ok = false;
		document.getElementById('titulo').style.background = "red";
	}
	if (estilo == "") {
		ok = false;
		document.getElementById('estilo').style.background = "red";
	}

	if (descripcion == "") {
		ok = false;
		document.getElementById('descripcion').style.background = "red";
	}

	if (fecha == "") {
		ok = false;
		document.getElementById('fecha').style.background = "red";
	}

	if (foto == "") {
		ok = false;
		document.getElementById('foto').style.background = "red";
	}

	if (ok == true) {
		document.getElementById('publicarRuta').submit();
	}
}

function pintarFormulario(datos) {
	
	document.getElementById("idruta").value = datos.idruta;
	document.getElementById("titulo").value = datos.titulo;
	document.getElementById("estilo").value = datos.estilo;
	document.getElementById("descripcion").value = datos.descripcion;
	document.getElementById("fecha").value = datos.fecha;
	document.getElementById("foto").value = datos.foto;
	
}

window.onload = function(){
	
	let idruta = getParameterByName("idruta");
	let op = getParameterByName("op");
	
	llamada(idruta,op);
	
}

