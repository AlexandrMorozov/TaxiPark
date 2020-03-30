function authorizationChecker()
{
    var login=document.getElementById('dob').value;
    var password=document.getElementById('dob1').value;
    var isValid=true;
    if(login.length>20 && login.length<4)
    {
        document.getElementById("alert1").innerHTML="* Длина логина должна составлять от 4 до 20 символов!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert1").value;
        if(v!=="")
        {
            document.getElementById("alert1").innerHTML="";
        }
    }
    if(/[^A-Za-z0-9]/.test(login))
    {
        document.getElementById("alert1").innerHTML="* Логин можеть содержать только буквы и цифры!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert1").value;
        if(v!=="")
        {
            document.getElementById("alert1").innerHTML="";
        }
    }
    if(password.length>15)
    {
        document.getElementById("alert2").innerHTML="* Длина пароля должна составлять от 4 до 15 символов!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert2").value;
        if(v!=="")
        {
            document.getElementById("alert2").innerHTML="";
        }
    }
    if(/[^A-Za-z0-9]/.test(password))
    {
        document.getElementById("alert2").innerHTML="* Пароль можеть содержать только буквы и цифры!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert2").value;
        if(v!=="")
        {
            document.getElementById("alert2").innerHTML="";
        }
    }
    return isValid;


}
function registrationChecker()
{
    var name=document.getElementById('name').value;
    var surname=document.getElementById('surname').value;
    var telephoneNumber=document.getElementById('telnum').value;
    var isValid=true;

    if(/[^A-Za-zА-Яа-я]/.test(name))
    {
        document.getElementById("alert3").innerHTML="* Имя должно содержать только буквы!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert3").value;
        if(v!=="")
        {
            document.getElementById("alert3").innerHTML="";
        }
    }
    if(/[^A-Za-zА-Яа-я]/.test(surname))
    {
        document.getElementById("alert5").innerHTML="* Фамилия должна содержать только буквы!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert5").value;
        if(v!=="")
        {
            document.getElementById("alert5").innerHTML="";
        }
    }
    if(/[^0-9]/.test(telephoneNumber))
    {
        document.getElementById("alert7").innerHTML="* Телефонный номер должен содержать только числа!<br>";
        isValid=false;
    }
    else
    {
        var v=document.getElementById("alert7").value;
        if(v!=="")
        {
            document.getElementById("alert7").innerHTML="";
        }
    }
    if(isValid==true && authorizationChecker()==true)
    {
        return true;
    }
    else
    {
        return false;
    }

}