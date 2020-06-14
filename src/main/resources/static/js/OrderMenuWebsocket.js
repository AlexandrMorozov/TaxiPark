
var orderSocket=new WebSocket('ws://localhost:8080/order/*/customer');
orderSocket.onmessage=onMessage;


Notification.requestPermission(function (permission)
{console.log('Результат запроса прав:', permission);});


function onMessage(event)
{
    var device = JSON.parse(event.data);

    if(device.action==="price_response")
    {
        var costDataHolder=document.getElementById("finalcost");
        costDataHolder.innerText=device.price;
    }
    else if(device.action==="taxi_order_answer")
    {
       // alert(device.comment)

        addMessage('Новое сообщение', {body: device.comment, dir: 'auto'});

        if(device.response===true)
        {
            document.location.href = "http://localhost:8080/";
        }
    }

}

function afterLoad(type)
{
    if(type===1)
    {
        sendPriceRequest();
    }
    else if(type===2)
    {
        sendCargoPriceRequest();
    }
}

function sendPriceRequest()
{
    if(document.getElementsByName("fromPoint")[0].value!=="" &&
        document.getElementsByName("toPoint")[0].value!=="")
    {
        var serviceID=document.getElementsByName("id")[0].value;
        var unitCost=document.getElementById("unitcost").innerText;

        var priceRequest={action: "price_request",serviceID: serviceID,unitCost: unitCost, type: "pas"};
        orderSocket.send(JSON.stringify(priceRequest));
    }

}

function sendCargoPriceRequest()
{
    if(document.getElementsByName("fromPoint")[0].value!=="" &&
        document.getElementsByName("toPoint")[0].value!==""
        && document.getElementsByName("weight")[0].value>0)
    {
        var serviceID=document.getElementsByName("id")[0].value;
        var unitCost=document.getElementById("unitcost").innerText;
        var weight=document.getElementsByName("weight")[0].value;

        var priceRequest={action: "price_request",serviceID: serviceID,unitCost: unitCost,weight: weight,type: "car"};
        orderSocket.send(JSON.stringify(priceRequest));
    }
}

function sendOrderRequest()
{
    if((document.getElementsByName("fromPoint")[0].value!=="" &&
        document.getElementsByName("toPoint")[0].value!=="")
        && (document.getElementById("logn").innerText!=="Войти"
            || document.getElementsByName("telephone")[0].value!==""))
    {
        let serviceID=document.getElementsByName("id")[0].value;
        let fromPoint=document.getElementsByName("fromPoint")[0].value;
        let toPoint=document.getElementsByName("toPoint")[0].value;
        let cost=document.getElementById("finalcost").innerText;
        let login;
        let telephone;

        if(document.getElementById("logn").innerText!=="Войти")
        {
            login=document.getElementById("logn").innerText;
            telephone=null;
        }
        else
        {
            login=null;
            telephone=document.getElementsByName("telephone")[0].value;
        }

        console.log(serviceID+" "+fromPoint+" "+toPoint+" "+cost+" "+login+" "+telephone);

        var orderRequest={action: "taxi_order_request",serviceID: serviceID,
            fromPoint: fromPoint, toPoint: toPoint, cost: cost, login: login,
            telephone: telephone};

        orderSocket.send(JSON.stringify(orderRequest));

    }
}

function sendCargoOrderRequest()
{
    if((document.getElementsByName("date")[0].value!=="" && document.getElementsByName("time")[0].value!=="" &&
    document.getElementsByName("fromPoint")[0].value!=="" && document.getElementsByName("toPoint")[0].value!=="" &&
        document.getElementsByName("weight")[0].value!=="") && (document.getElementById("logn").innerText!=="Войти"
        || document.getElementsByName("telephone")[0].value!==""))
    {

        let serviceID=document.getElementsByName("id")[0].value;
        let fromPoint=document.getElementsByName("fromPoint")[0].value;
        let toPoint=document.getElementsByName("toPoint")[0].value;
        let cost=document.getElementById("finalcost").innerText;
        let date=document.getElementsByName("date")[0].value;
        let time=document.getElementsByName("time")[0].value;
        let weight=document.getElementsByName("weight")[0].value;
        let comment=document.getElementsByName("comment")[0].value;
        let login;
        let telephone;

        if(document.getElementById("logn").innerText!=="Войти")
        {
            login=document.getElementById("logn").innerText;
            telephone=null;
        }
        else
        {
            login=null;
            telephone=document.getElementsByName("telephone")[0].value;
        }

        var orderRequest={action: "cargo_order_request",serviceID: serviceID,
            fromPoint: fromPoint, toPoint: toPoint, cost: cost, login: login,
            telephone: telephone, date: date, time: time, weight: weight,comment:comment};

        orderSocket.send(JSON.stringify(orderRequest));


    }

}

function sendServiceOrderRequest()
{
    if(document.getElementsByName("ordDate")[0].value!=="" &&
        (document.getElementById("logn").innerText!=="Войти"
        || document.getElementsByName("telephone")[0].value!==""))
    {
        let serviceID=document.getElementsByName("id")[0].value;
        let date=document.getElementsByName("ordDate")[0].value;
        let time;
        let comment=document.getElementsByName("comment")[0].value;
        let login;
        let telephone;

        for(let i=0;i<document.getElementsByName("orderTime").length;i++)
        {
            if(document.getElementsByName("orderTime")[i].checked)
            {
                time=document.getElementsByName("orderTime")[i].value;

                break;
            }
        }

        if(document.getElementById("logn").innerText!=="Войти")
        {
            login=document.getElementById("logn").innerText;
            telephone=null;
        }
        else
        {
            login=null;
            telephone=document.getElementsByName("telephone")[0].value;
        }

        console.log(serviceID+" "+" "+login+" "+telephone+" "+time+" "+date+" ");

        var orderRequest={action: "service_order_request",serviceID: serviceID, login: login,
            telephone: telephone, date: date, time: time,comment:comment};

        orderSocket.send(JSON.stringify(orderRequest));
    }
}

function addMessage(title, options)
{
    if (Notification.permission === "granted")
    {
        var notification = new Notification(title, options);
    }
    else if (Notification.permission !== 'denied')
    {
        Notification.requestPermission(
            function (permission)
            {
                if (permission === "granted")
                {
                    var notification = new Notification(title, options);
                }
            });
    }

}


