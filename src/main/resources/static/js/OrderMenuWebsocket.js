
var orderSocket=new WebSocket('ws://localhost:8080/order/aaa');
orderSocket.onmessage=onMessage;

function onMessage(event)
{
    var device = JSON.parse(event.data);

    if(device.action==="price_response")
    {
        var costDataHolder=document.getElementById("finalcost");
        costDataHolder.innerText=device.price;
    }
}

function sendPriceRequest()
{
    var serviceID=document.getElementsByName("id")[0].value;
    var unitCost=document.getElementById("unitcost").innerText;

    var priceRequest={action: "price_request",serviceID: serviceID,unitCost: unitCost};
    orderSocket.send(JSON.stringify(priceRequest));
}