
var orderSocket;

function enableEmployeeConnection()
{
    //console.log(document.getElementById("taxi").innerText);
    orderSocket=new WebSocket('ws://localhost:8080/order/'+document.getElementById("sender").innerText+'/employee');
    orderSocket.onmessage=onMessage;
}

function onMessage(event)
{
    var device = JSON.parse(event.data);

    if(device.action==="new_order_taxi")
    {
        let fixedTime=fixTimeReflection(device.time);
        let order=[device.serviceName,device.date,fixedTime,device.price,device.pod+'-'+device.poa];

        assignOrder(order,device.orderID);
    }
     if(device.action==="new_order_cargo")
     {
         let order=[device.serviceName,device.date,device.time,device.price,device.pod+'-'+device.poa,device.wgt];
         assignOrder(order,device.orderID);
     }
     if(device.action==="new_order_custom")
     {
         let order=[device.serviceName,device.date,device.time,device.price,device.comment];
         assignOrder(order,device.orderID);
     }
    /*if(device.action==="order_denial")
    {
        removeOrder(device.orderID);
    }*/
    if(device.action=="check_status")
    {
        let numOfOrders=countCurrentOrders();
        let sender=document.getElementById("sender").innerText;

        var response={action: "status_report", numOfOrders: numOfOrders, sender: sender};
        orderSocket.send(JSON.stringify(response));
    }

}


function countCurrentOrders()
{
    return document.getElementById("tbl").getElementsByTagName("tr").length;
}

function fixTimeReflection(orderTime)
{
    var time=orderTime;
    var fullTime=new Array();

    fullTime=time.split(':');

    fullTime[2]=Math.round(fullTime[2]);

    if(fullTime[2]<10)
    {
        fullTime[2]="0"+fullTime[2];
    }

    return fullTime[0]+":"+fullTime[1]+":"+fullTime[2];

}

/* function removeOrder(row)
 {
     document.getElementById(row).parentElement.removeChild(document.getElementById(row));
     //let r=row.closest("tr");
     //r.parentElement.removeChild(r);
 }*/

function assignOrder(order,orderID)
{
    let table=document.getElementById("tbl");

    var secondaryOrder = document.createElement('tr');
    secondaryOrder.setAttribute('id', orderID);

    for(let i=0;i<order.length;i++)
    {
        var td=document.createElement('td');

        if(i===3)
        {
            td.innerText=order[i]+" руб.";
        }
        else
        {
            td.innerText=order[i];
        }

        secondaryOrder.appendChild(td);
    }


    var td=document.createElement('td');
    var ref=document.createElement('a')
    var but=document.createElement("button");
    var link="/adminportal/completeorder?orderID="+orderID;
    ref.setAttribute("href",link);
    but.setAttribute("class","btn btn-lg btn-primary btn-block");
    but.innerText="Завершить";

    ref.appendChild(but);
    td.appendChild(ref);
    secondaryOrder.appendChild(td);

    table.appendChild(secondaryOrder);
}