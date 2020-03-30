function TimeTable(r)
{
    var i=new Date,l=[],u=null;
    function e()
    {
        if(null!=function()
        {var e=null;
        if(1<=i.getDay()&&i.getDay()<=5)
            if(l.weekdays)e="weekdays";
            else switch(i.getDay())
            {
                case 1:e="monday";break;
                case 2:e="tuesday";break;
                case 3:e="wednesday";break;
                case 4:e="thursday";break;
                case 5:e="friday";break;
                default:return null
            }
            else if(0==i.getDay()||6==i.getDay())
                if(l.weekends)e="weekends";
                else
                    switch(i.getDay())
                    {case 6:e="saturday";break;
                    case 7:e="sunday";break;
                    default:return null
                    }
                return u=function(e)
                {
                    var t=!1,a=0;
                    if(l[e])
                    for(var s=i.getHours();s<23;s++)
                    {
                        if(l[e][s]&&0<l[e][s].length)
                        {
                            t=!0;
                            for(var n=0;n<l[e][s].length;n++)
                            {
                                if(s!=i.getHours())
                                return a+=parseInt(l[e][s][n]);
                            if(l[e][s][n]>i.getMinutes())
                                return a=parseInt(l[e][s][n])-i.getMinutes()
                            }
                        }
                        s!=i.getHours()?a+=60:a+=60-i.getMinutes()
                    }
                return t?a:null
                }(e)
        }())
        {var e=r.getElementsByClassName("info time")[0];
        if(null!=u)
        {
            var t=((s=u)-s%(n=60))/n,a="";
            0<t&&(a+=t+" ч. "),a+=u-60*t+" мин.",e.getElementsByClassName("current")[0].innerText=a,e.classList.add("active")
        }
        else
            e.classList.remove("active")
        }
        var s,n
    }
    function n(e)
    {
        var t=e.getElementsByClassName("head"),a=(new Date).getHours(),s=[];
        if(t)
            for(var n=0;n<t.length;n++)
            {
                var r=t[n].getElementsByClassName("unit"),i=t[n].getElementsByClassName("hour")[0].innerText;
                if(a==i?t[n].className+=t[n].className?" active":"active":t[n].classList.remove("active"),r)
                    for(var l=0;l<r.length;l++)
                        s[i]||(s[i]=[]),s[i].push(r[l].getElementsByClassName("mm")[0].innerText)
            }
        return s
    }
    !function(e)
    {
        if(this.periods=e.getElementsByClassName("tab-panel"),this.periods)
            for(var t=0;t<this.periods.length;t++)
                for(var a=this.periods[t].className.split(" "),s=0;s<a.length;s++)
                    "tab-panel"!=a[s]&&(l[a[s]]=n(this.periods[t]))
    }
    (r),e(),setInterval(function(){i=new Date,e()},17500)
}
!function()
{
    var e=document.querySelector(".burger-container"),t=document.querySelector(".menu");
    e.onclick=function()
    {
        t.classList.toggle("menu-opened")
    }
}
();
var timeTables=document.getElementsByClassName("timetable");
if (timeTables)
    for(var i=0;i<timeTables.length;i++)
        new TimeTable(timeTables[i]);