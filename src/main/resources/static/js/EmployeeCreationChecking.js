function enableDriverData()
{


    if(document.getElementById("pr").value==="Водитель")
    {

        document.getElementById("driverData").hidden=false;
        //document.getElementById("isHaveDriverLicense").value="true";

        //document.getElementById("dob").disabled=true;

       /* document.getElementById("ln").disabled=true;
        document.getElementById("ib").disabled=true;
        document.getElementById("lvu").disabled=true;
        document.getElementById("mvu").disabled=true;*/

        document.getElementById("ln").disabled=false;
        document.getElementById("ib").disabled=false;
        document.getElementById("lvu").disabled=false;
        document.getElementById("mvu").disabled=false;
        document.getElementById("asc").disabled=false;


    }
    else
    {
        alert("ccc");
        document.getElementById("driverData").hidden=true;
        //document.getElementById("isHaveDriverLicense").value="false";


        //document.getElementById("dob").disabled='disabled';

        document.getElementById("ln").disabled=true;
        document.getElementById("ib").disabled=true;
        document.getElementById("lvu").disabled=true;
        document.getElementById("mvu").disabled=true;
        document.getElementById("asc").disabled=true;
    }



}

function checkDriverData()
{
   // var f=document.getElementById("dob");
   // document.getElementById("dob").disabled=false;

    if(document.getElementById("pr").value==="Водитель")
    {
        document.getElementById("driverData").hidden=false;
       // document.getElementById("isHaveDriverLicense").value="true";

        document.getElementById("ln").disabled=false;
        document.getElementById("ib").disabled=false;
        document.getElementById("lvu").disabled=false;
        document.getElementById("mvu").disabled=false;
        document.getElementById("asc").disabled=false;

        /*var f=document.getElementById("ln");
        alert(f);*/

        /*alert(document.getElementsByName("ln")[0]);
        document.getElementsByName("ln")[0].disabled=false;
        document.getElementsByName("ib")[0].disabled=false;
        document.getElementsByName("lvu")[0].disabled=false;
        document.getElementsByName("mvu")[0].disabled=false;*/

        /*document.getElementById("ln").value="sdf";
        document.getElementById("ib").value="sdf";
        document.getElementById("lvu").value="dfg";
        document.getElementById("mvu").value="sdfdg";*/
    }
}

function f()
{

    document.getElementById("ln").disabled=false;
    document.getElementById("ib").disabled=false;
    document.getElementById("lvu").disabled=false;
    document.getElementById("mvu").disabled=false;
    document.getElementById("asc").disabled=false;

    /*document.getElementById("ln").value="sdf";
    document.getElementById("ib").value="sdf";
    document.getElementById("lvu").value="dfg";
    document.getElementById("mvu").value="sdfdg";*/
}