var xmlhttp = new getXMLObject();	//xmlhttp holds the ajax object

function getXMLObject(){ //XML OBJECT
    var xmlHttp = false;
    try{
	       xmlHttp = new ActiveXObject("Msxml2.XMLHTTP") // For Old Microsoft Browsers
    }
	catch(e1){
		try{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") // For Microsoft IE 6.0+
		}
		catch (e2){
			xmlHttp = false // No Browser accepts the XMLHTTP Object then false
		}
	}
	if(!xmlHttp && typeof XMLHttpRequest != 'undefined'){
	          xmlHttp = new XMLHttpRequest(); //For Mozilla, Opera Browsers
    }
    return xmlHttp; // Mandatory Statement returning the ajax object created
}



var hide1id, show12, showr, hide2id, show3id, hider1;

$().ready(function(){
    //jQuery function

    hide1id = function(){
        $("#1id").hide();
    }

    showr = function(){
        $("#r1").fadeIn("slow");
    }

    show12 = function(){
        $("#10").fadeIn("slow");
        $("#11").fadeIn("slow");
        $("#12").fadeIn("slow");
    }

    hide2id = function(){
        $("#2id").hide();
    }

    show3id = function(){
        $("#3id").fadeIn("slow");
    }


        


    /*
    hides = function(){
        $("#status").fadeOut("fast");
    }
    shows = function(){
        $("#status").fadeIn();
    }
    hidee = function(){
        $("#stderr_row").fadeOut("fast");
    }
    showe = function(){
        $("#stderr_row").fadeIn();
    }
    showm = function(){
        $("#linkdetail").modal("toggle");
    }
    */

})


function go1() {
    hide1id();
    showr();

    document.getElementById("x").style.display = "none";
}

function go2() {
/*
    var a = document.getElementById("c1a").value;
    var b = document.getElementById("c2a").value;
    var c = document.getElementById("amt").value;
    if(c > a+b){
        alert("Pay full amount Bro !")
        return;
    }
    if(c < a+b){
        alert(" I am happy to take more, but my dev doesnt allow")
        return;
    }
    */

    show3id();
    show12();
    pay();    
    hide2id();
}















var state1="";
var state2="";

function handleServerResponse1(){
    if(xmlhttp.readyState == 4){
        if(xmlhttp.status == 200){
            var store = xmlhttp.responseText.trim();
            var n = store.indexOf("|");
            state1 = store.substring(0,n);
            state2 = store.substring(n+1,store.length);

            alert(state1+" - "+state2);

        }
    }
}

function pay(){

    var c1p = document.getElementById("c1p").value;
    var c2p = document.getElementById("c2p").value;

    if(xmlhttp){
        xmlhttp.open("POST","pay/",true); //getname will be the servlet name
        // / after create cos /create/ will be the result and this is how it is mapped
        xmlhttp.onreadystatechange  = handleServerResponse1;
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send("&c1p="+c1p+"&c2p="+c2p); //Posting url and custom to Servlet
    }


}


var tok1="";
var tok2="";

function handleServerResponse2(){
    if(xmlhttp.readyState == 4){
        if(xmlhttp.status == 200){
            var store = xmlhttp.responseText.trim();

            var n = store.indexOf("|");
            tok1 = store.substring(0,n);
            tok2 = store.substring(n+1,store.length);

            alert("access_token1 = "+tok1+"\naccess_token2 = "+tok2);

            process_it();
            
        }
    }
}

function pay1(){
    document.getElementById("cross1").style.display = "none";
    document.getElementById("cross2").style.display = "none";
    document.getElementById("tick1").style.display = "none";
    document.getElementById("tick2").style.display = "none";


    var c1o = document.getElementById("c1o").value;
    var c2o = document.getElementById("c2o").value;

    if(xmlhttp){
        xmlhttp.open("POST","pay1/",true); //getname will be the servlet name
        // / after create cos /create/ will be the result and this is how it is mapped
        xmlhttp.onreadystatechange  = handleServerResponse2;
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send("&c1o="+c1o+"&c2o="+c2o+"&c1s="+state1+"&c2s="+state2); //Posting url and custom to Servlet
    }


}

function process_it(){
    if(tok1 == "-1"){
        document.getElementById("cross1").style.display = "block"
    }
    else{
        document.getElementById("tick1").style.display = "block"
    }

    if(tok2 == "-1"){
        document.getElementById("cross2").style.display = "block"
    }
    else{
        document.getElementById("tick2").style.display = "block"
    }

    if( tok1 == "-1" || tok2 == "-1"){
        failure();
    }
    else{
        success();
    }

}


function success(){
    document.getElementById("3id").style.display = "none";
    document.getElementById("success").style.display = "block";
}

function failure(){
    document.getElementById("3id").style.display = "none";
    document.getElementById("failure").style.display = "block";
    
}



