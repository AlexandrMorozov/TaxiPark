
var orderSocket=new WebSocket('ws://localhost:8080/order/*/customer');
orderSocket.onmessage=onMessage;

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
        alert(device.comment)

        if(device.response===true)
        {
            document.location.href = "http://localhost:8080/";
        }
        /*add("action","taxi_order_answer")
            .add("response", false)
            .add("comment","Вы не можете сделать заказ, при наличии исполняемого заказа подобного типа")*/
    }

}

function afterLoad()
{
    sendPriceRequest();
}

function sendPriceRequest()
{
    if(document.getElementsByName("fromPoint")[0].value!=="" ||
        document.getElementsByName("toPoint")[0].value!=="")
    {
        var serviceID=document.getElementsByName("id")[0].value;
        var unitCost=document.getElementById("unitcost").innerText;

        var priceRequest={action: "price_request",serviceID: serviceID,unitCost: unitCost};
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