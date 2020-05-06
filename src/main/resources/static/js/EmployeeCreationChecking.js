function enableDriverData()
{


    if(document.getElementById("pr").value==="Водитель")
    {

        document.getElementById("driverData").hidden=false;

        document.getElementById("ln").disabled=false;
        document.getElementById("ib").disabled=false;
        document.getElementById("lvu").disabled=false;
        document.getElementById("mvu").disabled=false;
        document.getElementById("asc").disabled=false;


    }
    else
    {
        document.getElementById("driverData").hidden=true;

        document.getElementById("ln").disabled=true;
        document.getElementById("ib").disabled=true;
        document.getElementById("lvu").disabled=true;
        document.getElementById("mvu").disabled=true;
        document.getElementById("asc").disabled=true;
    }



}

function checkDriverData()
{

    if(document.getElementById("pr").value==="Водитель")
    {
        document.getElementById("driverData").hidden=false;

        document.getElementById("ln").disabled=false;
        document.getElementById("ib").disabled=false;
        document.getElementById("lvu").disabled=false;
        document.getElementById("mvu").disabled=false;
        document.getElementById("asc").disabled=false;

    }
}

function f()
{

    document.getElementById("ln").disabled=false;
    document.getElementById("ib").disabled=false;
    document.getElementById("lvu").disabled=false;
    document.getElementById("mvu").disabled=false;
    document.getElementById("asc").disabled=false;

}