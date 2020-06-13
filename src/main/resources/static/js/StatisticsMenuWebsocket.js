var orderSocket=new WebSocket('ws://localhost:8080/statistics');
orderSocket.onmessage=onMessage;

console.log("q1");

/* function q()
 {
     var priceRequest={action: "all_orders",period: 'week',from: '2019-01-06',to: '2019-01-12'};
     orderSocket.send(JSON.stringify(priceRequest));
 }*/

function sendRequest()
{
    console.log("henlo")

    let currentDate=new Date();

    let action;

    let target;

    let period;



    let q1=document.getElementsByName("scale");
    let q2=document.getElementsByName("period");
    let q3=document.getElementsByName("value");

    let selectedPeriodIndex=document.getElementById("period").options.selectedIndex;
    let selectedPeriodValue=document.getElementById("period").options[selectedPeriodIndex].value;

    let scaleValue=findCheckedValue(q1);
    let periodValue=findCheckedValue(q2);
    let valueValue=findCheckedValue(q3);

    if(periodValue=="pres")
    {
        period=calculatePeriod(selectedPeriodValue,currentDate);
    }
    else if(periodValue=="prev")
    {
        if(selectedPeriodValue=="week")
        {
            currentDate.setDate(currentDate.getDate()-7);
        }
        else if(selectedPeriodValue=="month")
        {
            currentDate.setMonth(currentDate.getMonth()-1);
        }
        else if(selectedPeriodValue=="year")
        {
            currentDate.setFullYear(currentDate.getFullYear()-1);
        }

        period=calculatePeriod(selectedPeriodValue,currentDate);
    }
    else if(periodValue=="manual")
    {
        /* calculatePeriod(selectedPeriodValue,);*/
        let date;

        let selectedYearIndex=document.getElementById("yr").options.selectedIndex;
        let selectedYearValue=document.getElementById("yr").options[selectedYearIndex].value;

        if(selectedPeriodValue=="year")
        {
            date=new Date(selectedYearValue,1,1);
        }
        else if(selectedPeriodValue=="month")
        {
            let selectedMonthIndex=document.getElementById("mth").options.selectedIndex;
            let selectedMonthValue=document.getElementById("mth").options[selectedMonthIndex].value;

            date=new Date(selectedYearValue,selectedMonthValue,1);

        }

        period=calculatePeriod(selectedPeriodValue,date);

    }

    if(scaleValue=="gen" && valueValue=="inc")
    {

        action="all_payments";
        /*all_payments*/
    }
    else if(scaleValue=="cat" && valueValue=="number")
    {
        action="orders_by_category";

        let targetIndex=document.getElementById("ctg").options.selectedIndex;
        target=document.getElementById("ctg").options[targetIndex].value;
        /*orders_by_category*/
    }
    /* else if(scaleValue=="serv" && valueValue=="inc")
     {
         action="payments_by_service";

         let targetIndex=document.getElementById("srv").options.selectedIndex;
         target=document.getElementById("srv").options[targetIndex].value;
     }*/
    else if(scaleValue=="gen" && valueValue=="number")
    {
        action="all_orders";
        /*all_orders*/
    }
    else if(scaleValue=="cat" && valueValue=="inc")
    {
        action="payments_by_category";
        /*payments_by_category*/

        let targetIndex=document.getElementById("ctg").options.selectedIndex;
        target=document.getElementById("ctg").options[targetIndex].value;
    }
    /*else if(scaleValue=="serv" && valueValue=="number")
    {
        action="orders_by_service";


        let targetIndex=document.getElementById("srv").options.selectedIndex;
        target=document.getElementById("srv").options[targetIndex].value;
    }*/

    if((scaleValue=="gen" && valueValue=="number") || (scaleValue=="gen" && valueValue=="inc"))
    {
        var priceRequest={action: action,period: selectedPeriodValue,
            from: period[0].getFullYear()+"-"+period[0].getMonth()+"-"+period[0].getDate(),
            to: period[1].getFullYear()+"-"+period[1].getMonth()+"-"+period[1].getDate()};

        orderSocket.send(JSON.stringify(priceRequest));
    }
    else
    {

        var priceRequest={action: action,period: selectedPeriodValue, target: target,
            from: period[0].getFullYear()+"-"+period[0].getMonth()+"-"+period[0].getDate(),
            to: period[1].getFullYear()+"-"+period[1].getMonth()+"-"+period[1].getDate()};

        orderSocket.send(JSON.stringify(priceRequest));
    }

}

function onMessage(event)
{
    var device = JSON.parse(event.data);

    let fx1=[];
    let fy1=[];
    let fx2=[];
    let fy2=[];

    let a1=[];
    let a2=[];

    if(device.action==="response")
    {
        console.log(device.f1);
        console.log(device.f2);

        for(let i=0;i<device.f1.length;i++)
        {
            fx1[i]=device.f1[i].date;
            fy1[i]=device.f1[i].value;

            if(device.f2.length>0)
            {
                fx2[i]=device.f2[i].date;
                fy2[i]=device.f2[i].value;
            }

        }

        drawChart(fx1,fy1,fx2,fy2);

    }
    else if(device.action==="category_response")
    {
        let categrySelect=document.getElementById("ctg");

        categrySelect.options.length=0;

        for(let i=0;i<device.categories.length;i++)
        {
            var categoryName=device.categories[i].categoryName;

            var option = document.createElement('option');
            var descr = document.createTextNode(categoryName);
            option.setAttribute('value', categoryName);
            option.appendChild(descr);
            /*document.getElementById("ctg")*/categrySelect.appendChild(option);
        }


    }
    else if(device.action==="year_response")
    {
        let categrySelect=document.getElementById("yr");

        categrySelect.options.length=0;

        for(let i=0;i<device.years.length;i++)
        {
            var year=device.years[i].year;

            var option = document.createElement('option');
            var descr = document.createTextNode(year);
            option.setAttribute('value', year);
            option.appendChild(descr);
            /*document.getElementById("ctg")*/categrySelect.appendChild(option);
        }
    }
}

function calculatePeriod(period,date)
{
    let frm=new Date();
    let to=new Date();

    if(period=="week")
    {
        let dayOfTheWeek=date.getDay();

        if(dayOfTheWeek==0)
        {
            frm.setDate(date.getDate()-6);
            to=date;
        }
        else
        {
            frm.setDate(date.getDate()-(dayOfTheWeek-1));
            to.setDate(date.getDate()+(7-dayOfTheWeek));
        }
    }
    else if(period=="month")
    {
        let xx=new Date(date.getFullYear(),date.getMonth(),0);

        frm=new Date(date.getFullYear(),date.getMonth(),1);
        to=new Date(date.getFullYear(),date.getMonth(),xx.getDate());
    }
    else if(period=="year")
    {
        frm=new Date(date.getFullYear(),1,1);
        to=new Date(date.getFullYear(),12,31);
    }

    let listOfPeriods=[frm,to];

    return listOfPeriods;
}

function findCheckedValue(array)
{
    for(let i=0;i<array.length;i++)
    {
        if(array[i].checked==true)
        {
            return array[i].value;
        }
    }
}



function drawChart(fx1,fy1,fx2,fy2)
{
    var f1={
        labels: fx1,
        series: [fy1,fy2],
    };

    var options = {
        width: 900,
        height: 500
    };

    new Chartist.Line('.ct-chart', f1,options);
}

function drawInitChart()
{
    var f1={
        labels: [1,2,3,4,5,6,7],
        series: [],
    };

    var options = {
        width: 900,
        height: 500
    };

    new Chartist.Line('.ct-chart', f1,options);
}

function hideSelectionMenus(arrayOfMenus)
{
    for(let i=0;i<arrayOfMenus.length;i++)
    {
        arrayOfMenus[i].hidden=true;
    }
}

function showPeriodSelectionMenus(numOfMenu)
{
    let arrayOfMenus=document.getElementById("menucont1").getElementsByTagName("div");
    console.log("length "+arrayOfMenus.length);


    if(numOfMenu==0 || numOfMenu==1)
    {
        hideSelectionMenus(arrayOfMenus);
        //
        //sendRequest();
    }
    else if(numOfMenu==2)
    {
        let selectedPeriodIndex=document.getElementById("period").options.selectedIndex;

        console.log(selectedPeriodIndex)

        if(selectedPeriodIndex===1)
        {
            console.log(2);

            for (let i=0;i<arrayOfMenus.length;i++)
            {
                arrayOfMenus[i].hidden=false;
            }

            var request={action: "all_years",obj:"qqqqqqqqqqqqqqqqqqqqqq"};
            orderSocket.send(JSON.stringify(request));

        }
        else if(selectedPeriodIndex===2)
        {
            let newArray=Array();
            newArray[0]=arrayOfMenus[1];

            hideSelectionMenus(newArray);
            arrayOfMenus[0].hidden=false;


            var request={action: "all_years"};
            orderSocket.send(JSON.stringify(request));


        }
    }
}

function showScopeSelectionMenus(numOfMenu)
{
    let arrayOfMenus=document.getElementById("menucont").getElementsByTagName("div");
    console.log("length "+arrayOfMenus.length);

    if (numOfMenu==0)
    {
        hideSelectionMenus(arrayOfMenus);
        //
        //sendRequest();
    }
    else if(numOfMenu==1)
    {
        let newArray=Array();
        newArray[0]=arrayOfMenus[0];

        hideSelectionMenus(newArray);
        arrayOfMenus[0].hidden=false;

        var request={action: "all_categories"};
        orderSocket.send(JSON.stringify(request));
    }
    /*else if(numOfMenu==2)
    {
        console.log(2);

        for (let i=0;i<arrayOfMenus.length;i++)
        {
            arrayOfMenus[i].hidden=false;
        }
    }*/
}

function onRefresh()
{
    //drawChart();
    console.log("q2");

    changePeriodMenu();

    let arrayOfRadiobuttonStates=[
        document.getElementById("gd").checked,
        document.getElementById("sc").checked
        /*document.getElementById("ss").checked*/];


    for(let i=0;i<arrayOfRadiobuttonStates.length;i++)
    {
        if(arrayOfRadiobuttonStates[i]==true)
        {
            showScopeSelectionMenus(i);
            break;
        }
    }

    // sendRequest();
    //q()
    drawInitChart();


}

function changePeriodMenu()
{
    let selectedPeriodIndex=document.getElementById("period").options.selectedIndex;
    let selectedPeriodValue=document.getElementById("period").options[selectedPeriodIndex].value;
    let arrayOfCells=document.getElementById("periodrangeoptions").getElementsByTagName("div");

    if(selectedPeriodValue==="week")
    {

        arrayOfCells[0].children[1].innerHTML="Текущая неделя";
        arrayOfCells[1].children[1].innerHTML="Предыдущая неделя";
        arrayOfCells[0].children[0].checked=true;

        arrayOfCells[2].children[0].hidden=true;
        arrayOfCells[2].children[1].hidden=true;
    }
    else if(selectedPeriodValue==="month")
    {
        arrayOfCells[0].children[1].innerHTML="Текущий месяц";
        arrayOfCells[1].children[1].innerHTML="Предыдущий месяц";


        arrayOfCells[2].children[0].hidden=false;
        arrayOfCells[2].children[1].hidden=false;
    }
    else if(selectedPeriodValue==="year")
    {
        arrayOfCells[0].children[1].innerHTML="Текущий год";
        arrayOfCells[1].children[1].innerHTML="Предыдущий год";

        arrayOfCells[2].children[0].hidden=false;
        arrayOfCells[2].children[1].hidden=false;
    }

    /*///////////////////////////////////*/
    let arrayOfRadiobuttonStates1=[
        document.getElementById("cp").checked,
        document.getElementById("pp").checked,
        document.getElementById("sp").checked];

    for (let i=0;i<arrayOfRadiobuttonStates1.length;i++)
    {
        if(arrayOfRadiobuttonStates1[i]==true)
        {
            showPeriodSelectionMenus(i);
            break;
        }
    }
}