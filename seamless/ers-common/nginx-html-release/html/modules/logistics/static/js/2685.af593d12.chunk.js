(self.webpackChunk_unified_logistics=self.webpackChunk_unified_logistics||[]).push([[2685],{6677:(e,t,a)=>{"use strict";a.d(t,{xn:()=>de,R$:()=>oe,Fv:()=>ve});var r=a(7560),n=a(1746),o=a(4315),l=a(5167),i=a(282),s=a(1749),c=a(4104),d=a.n(c),u=a(7801),g=a(1120),p=a(5117),f=a(2411),m=a.n(f),v=a(9609),h=a(6277),b=a(1416);const E=a.n(b)();var y=a(4126),Z="/billing/v1";function S(e){return new Promise((function(t,a){return E("/dms/v1/resellers/resellerChildren/".concat(e,"?resellerType=3pl_agents")).then((function(e){0!=(null==e?void 0:e.resellers.filter((function(e){var t;return"Active"==(null==e||null===(t=e.resellerData)||void 0===t?void 0:t.status)}))).length?t(e):a("Vendor not found")}))}))}var C="/billing/v1",w=a(9895),I=a(2318),T=(0,g.Z)((function(e){return(0,p.Z)({root:{padding:e.spacing(2),width:250,height:150,display:"flex",flexDirection:"row",justifyContent:"space-between",borderRadius:"5"},icon:{marginTop:e.spacing(2)},typo:{marginTop:e.spacing(5)},heading:{marginTop:e.spacing(3),fontSize:25}})}));const N=function(e){var t=e.children,a=e.label,r=e.value,n=e.className,o=e.gridClass,l=T();return f.createElement(w.Z,{className:(0,h.default)(l.root,n)},f.createElement(s.Z,{container:!0},f.createElement(s.Z,{container:!0,spacing:1},f.createElement(s.Z,{item:!0,xs:8},f.createElement(I.Z,{className:(0,h.default)(l.heading,o),variant:"h4"},a)),f.createElement(s.Z,{item:!0,xs:4,className:l.icon},t)),f.createElement(s.Z,null,f.createElement(I.Z,{variant:"caption"},r))))};var k=a(4664),D=a(9306),M=a(5405),F=a(1942),P=a(8670);const x=function(){return(0,P.useSelector)((function(e){return e.auth}),P.shallowEqual).user};var z=a(9721);const O=function(){var e=(0,z.useSnackbar)().enqueueSnackbar,t=(0,v.useTranslation)().t;function a(e){var a=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(a=e[t])})),a}return{showSuccessMessage:function(t){e("string"!=typeof t?a(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?a(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?a(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?a(t):t,{variant:"warning"})}}};var V=a(773),B=a(1564),_=a(9590),R=a(4074),W=(0,g.Z)((function(e){return(0,p.Z)({dateField:{marginTop:e.spacing(1)},root:{"& .MuiInputBase-root":{"& .MuiSelect-select":{backgroundColor:"white"}}}})}));const H=function(e){var t=e.vendor,a=e.vendorList,r=e.fromDate,n=e.toDate,o=e.setVendor,l=e.setFromDate,i=e.setToDate,c=e.isVendor,d=W(),u=(0,v.useTranslation)().t,g=window.config.openCalendarWith||"year";return f.createElement(s.Z,{container:!0,spacing:2},c?f.createElement(f.Fragment,null):f.createElement(s.Z,{item:!0,xs:12,sm:6,md:4},f.createElement(V.SeamlessDropDown,{className:(0,h.default)(d.dateField,d.root),onChange:function(e){o(e.target.value)},value:t,fullWidth:!0,label:u("vendor"),options:a})),f.createElement(B.M,{utils:R.Z},f.createElement(s.Z,{item:!0,xs:12,sm:6,md:4},f.createElement(_.e,{className:(0,h.default)(d.dateField,d.root),views:["year","month","date"],openTo:g,fullWidth:!0,autoOk:!0,variant:"inline",format:"yyyy-MM-dd",margin:"normal",label:u("date(from)"),name:"fromDate",value:r||null,onChange:function(e){l(null==e?void 0:e.toISOString())},KeyboardButtonProps:{"aria-label":"change date"},disableFuture:!0}))),f.createElement(B.M,{utils:R.Z},f.createElement(s.Z,{item:!0,xs:12,sm:6,md:4},f.createElement(_.e,{className:(0,h.default)(d.dateField),views:["year","month","date"],openTo:g,fullWidth:!0,autoOk:!0,variant:"inline",format:"yyyy-MM-dd",margin:"normal",label:u("date(to)"),name:"toDate",value:n||null,onChange:function(e){i(null==e?void 0:e.toISOString())},KeyboardButtonProps:{"aria-label":"change date"},minDate:r,disableFuture:!0}))))};var L=a(5238),q=a(3258),j=a(1744),U=a.n(j),Q=a(4278),Y=a(1807),K=(0,g.Z)((function(e){return(0,p.Z)({check:{"& .MuiCheckbox-root svg":{backgroundColor:"white",color:"black"},"& .MuiCheckbox-root.MuiCheckbox-indeterminate .MuiIconButton-label:after":{backgroundColor:"darkgrey"}}})}));const A=function(e){var t=e.trips,a=e.selectedTrips,r=e.setSelectedTrips,o=e.setTripsCount,l=e.setTotalFare,i=e.totalFare,s=e.loading,c=e.totalCount,d=e.pendingTripCount,u=e.pendingTripFare,g=e.pendingTrips,p=(0,L.Z)(e,["trips","selectedTrips","setSelectedTrips","setTripsCount","setTotalFare","totalFare","loading","totalCount","pendingTripCount","pendingTripFare","pendingTrips"]),m=(0,v.useTranslation)().t,h=K(),b=p.page,E=p.perPage,y=p.onPageChange,Z=p.onPageSizeChange,S=[{field:"checkbox",filterable:!1,sortable:!1,renderHeader:function(){return f.createElement(q.Z,{onChange:function(){a.length===d?(r([]),o(0),l(0)):(r(g),o(d),l(u))},checked:a.length===d})},width:70,hideSortIcons:!0,disableColumnMenu:!0,renderCell:function(e){return f.createElement("div",{className:"closed"==e.row.billingStatus?h.check:""},f.createElement(q.Z,{indeterminate:!("pending"==e.row.billingStatus),disabled:!("pending"==e.row.billingStatus),onChange:function(){return function(e,t){var s=a;if(s.includes(e)){var c=s.filter((function(t){return t!==e}));r(c);var d=i-t.tripFare;l(d),o(c.length)}else{var u=i+t.tripFare;l(u),r([].concat((0,n.Z)(s),[e])),o(s.length+1)}}(e.row.tripId,e.row)},checked:a.includes(e.row.tripId)||!("pending"==e.row.billingStatus)}))}},{field:"tripId",headerName:m("tripId"),sortable:!1,filterable:!0,width:100},{field:"pickupLocation",headerName:m("pickUpLocation"),sortable:!1,filterable:!0,width:130},{field:"dropLocation",headerName:m("dropLocation"),sortable:!1,filterable:!0,width:130},{field:"date",headerName:m("date"),sortable:!1,filterable:!0,width:150,renderCell:function(e){return f.createElement("div",null,e.getValue("date")?(0,Q.Z)((0,Y.Z)(new Date(e.getValue("date")).toISOString()),"yyyy-MM-dd HH:mm:ss"):"")}},{field:"tripFare",headerName:m("tripFare"),sortable:!1,filterable:!0,width:150},{field:"invoiceId",headerName:m("invoiceId"),sortable:!1,filterable:!0,width:150},{field:"vendor",headerName:m("vendor"),sortable:!1,filterable:!1,width:150},{field:"status",headerName:m("status"),sortable:!1,filterable:!0,width:100,renderCell:function(e){return"requested"===e.row.billingStatus?f.createElement(I.Z,{variant:"body2",style:{color:"#8B8000"}},m("requested")):"closed"===e.row.billingStatus?f.createElement(I.Z,{variant:"body2",style:{color:"green"}},m("closed")):"pending"===e.row.billingStatus?f.createElement(I.Z,{variant:"body2",style:{color:"#F56E16"}},m("pending")):"partially closed"===e.row.billingStatus?f.createElement(I.Z,{variant:"body2",style:{color:"#4B0082"}},m("partiallyClosed")):f.createElement(I.Z,{variant:"body2",style:{color:"#551436"}},e.row.billingStatus)},flex:1}];return f.createElement(U(),{rows:t,getRowId:function(e){return e.tripId},columns:S,rowCount:c,onPageSizeChange:function(e){Z(e.pageSize)},onPageChange:function(e){y(e.page)},pagination:!0,paginationMode:"server",page:b,pageSize:E,loading:s,disableSelectionOnClick:!0,columnBuffer:3})};var $=a(2663),G=a(6856),J=a(9525),X=a(7212),ee=a(6083),te=(0,g.Z)((function(e){return(0,p.Z)({dialogHeader:{fontSize:18,fontWeight:"bold",align:"left"},dialogContent:{fontSize:14},blurBackDrop:{backdropFilter:"blur(2px)",backgroundColor:"rgba(0, 0, 0, 0.2)"}})}));const ae=function(e){var t=e.setDialogOpen,a=e.dialogOpen,r=e.invoiceId,n=te(),o=(0,v.useTranslation)().t;return m().createElement("div",null,m().createElement($.Z,{open:a,keepMounted:!0,maxWidth:"md",onClose:function(){return t(!1)},BackdropProps:{classes:{root:n.blurBackDrop}}},m().createElement(ee.Z,{className:n.dialogHeader,disableTypography:!0},o("invoiceRequestSuccessful")),m().createElement(J.Z,null,m().createElement(X.Z,{className:n.dialogContent},m().createElement(I.Z,null,o("invoiceId")+" : "+r))),m().createElement(G.Z,null,m().createElement(i.Z,{variant:"outlined",color:"primary",onClick:function(){return t(!1)}},o("close")))))};var re=a(8049),ne=(0,g.Z)((function(e){return(0,p.Z)({stat:{margin:e.spacing(1)},icon:{width:300},rowSpace:{marginTop:e.spacing(3),marginBottom:e.spacing(2)},box:{backgroundColor:"rgb(245, 245, 245)!important"},icons:{fontSize:60,color:"white",borderRadius:10},vendorIcon:{backgroundColor:e.palette.primary.main},tripsIcon:{backgroundColor:"#ff5722"},costIcon:{backgroundColor:"#088F8F"},periodIcon:{backgroundColor:"#ff0081"},title:{marginTop:e.spacing(0)},button:{marginTop:e.spacing(3)}})}));const oe=function(){var e,t,a,c,g=ne(),p=(0,re.useHistory)(),m=d()().checkPermission,b=(0,v.useTranslation)().t,C=f.useState(""),w=(0,o.Z)(C,2),I=w[0],T=w[1],P=f.useState([{key:b("SelectVendor"),value:""}]),z=(0,o.Z)(P,2),B=z[0],_=z[1],R=f.useState(""),W=(0,o.Z)(R,2),L=W[0],q=W[1],U=f.useState(""),Q=(0,o.Z)(U,2),Y=Q[0],K=Q[1],$=f.useState(0),G=(0,o.Z)($,2),J=G[0],X=G[1],ee=f.useState(0),te=(0,o.Z)(ee,2),oe=te[0],le=te[1],ie=f.useState([]),se=(0,o.Z)(ie,2),ce=se[0],de=se[1],ue=f.useState([]),ge=(0,o.Z)(ue,2),pe=ge[0],fe=ge[1],me=f.useState(!1),ve=(0,o.Z)(me,2),he=ve[0],be=ve[1],Ee=f.useState(0),ye=(0,o.Z)(Ee,2),Ze=ye[0],Se=ye[1],Ce=f.useState([]),we=(0,o.Z)(Ce,2),Ie=we[0],Te=we[1],Ne=f.useState(0),ke=(0,o.Z)(Ne,2),De=ke[0],Me=ke[1],Fe=f.useState(0),Pe=(0,o.Z)(Fe,2),xe=Pe[0],ze=Pe[1],Oe=f.useState(0),Ve=(0,o.Z)(Oe,2),Be=Ve[0],_e=Ve[1],Re=f.useState(!1),We=(0,o.Z)(Re,2),He=We[0],Le=We[1],qe=x(),je=null===(e=qe.info)||void 0===e||null===(t=e.resellerData)||void 0===t?void 0:t.resellerName,Ue=null===(a=qe.info)||void 0===a||null===(c=a.resellerData)||void 0===c?void 0:c.resellerId,Qe=O(),Ye=Qe.showErrorMessage,Ke=Qe.showInfoMessage,Ae="3pl_agents"===qe.resellerType,$e=(0,j.useServerTable)({page:0,perPage:20,order:"desc",orderBy:"",filter:{}});function Ge(){if(be(!0),Ae||I){var e=Ae||I.split("--")[1];(t={vendor:Ae?Ue:e,billing_status:"pending",fromDate:L,toDate:Y},a="".concat(Z,"/tripData?").concat(y.stringify(t)),E(a)).then((function(e){0===e.resultCode?(Te(e.tripIds),Me(e.totalFare),ze(e.totalTrips)):(de([]),Te([]),ze(0))})),function(e){var t="".concat(Z,"/trip?").concat(y.stringify(e));return E(t)}({vendor:Ae?Ue:e,fromDate:L,toDate:Y,pageNumber:$e.page,pageSize:$e.perPage}).then((function(e){0===e.resultCode?(Se(e.totalTrips),de(e.trips)):(de([]),Ke(e.resultMessage))})).catch((function(e){Ye(e)}))}var t,a;be(!1)}return f.useEffect((function(){Ae?T(Ue):S("operator").then((function(e){if(0===e.resultCode){var t=e.resellers.map((function(e){return{key:e.resellerData.resellerName,value:e.resellerData.resellerName+"--"+e.resellerData.resellerId}}));_([{key:b("SelectVendor"),value:""}].concat((0,n.Z)(t)))}else _([{key:b("SelectVendor"),value:""}]),Ke(e.resultMessage)})).catch((function(e){Ye(e.resultMessage)}))}),[]),f.useEffect((function(){(I||L||Y)&&Ge()}),[I,L,Y,$e.perPage,$e.page]),f.createElement(f.Fragment,null,f.createElement(s.Z,{container:!0,spacing:2},f.createElement(s.Z,{item:!0,xs:12,md:9},f.createElement(H,{isVendor:Ae,vendorList:B,vendor:I,setVendor:T,fromDate:L,toDate:Y,setFromDate:q,setToDate:K})),f.createElement(s.Z,{item:!0,xs:12,md:3},f.createElement(V.SeamlessButton,{type:"submit",onClick:function(){p.push("/home/logistics/billing")},className:(0,h.default)(g.button),color:"secondary",variant:"outlined"},b("resetSearch")))),f.createElement(s.Z,{container:!0,className:(0,h.default)(g.rowSpace,g.box)},f.createElement(l.Z,{display:"flex",flexDirection:"row",flexWrap:"wrap",justifyContent:"space-evenly"},f.createElement(N,{className:g.stat,label:b("vendor"),value:Ae?je:I.split("--")[0]||"-"},f.createElement(k.Z,{className:(0,h.default)(g.icons,g.vendorIcon),style:{color:"white",fontSize:60}})),f.createElement(N,{className:g.stat,label:b("totalFare"),value:oe},f.createElement(M.Z,{className:(0,h.default)(g.icons,g.costIcon),style:{borderRadius:10,color:"white",fontSize:60}})),f.createElement(N,{className:g.stat,label:b("period"),value:L.slice(0,10)+"..."+Y.slice(0,10)},f.createElement(F.Z,{className:(0,h.default)(g.icons,g.periodIcon),style:{color:"white",fontSize:60}})),f.createElement(N,{className:(0,h.default)(g.stat,g.icon),gridClass:g.title,label:b("noOfTrips"),value:J},f.createElement(D.Z,{className:(0,h.default)(g.icons,g.tripsIcon),style:{color:"white",fontSize:60}})))),f.createElement(l.Z,{display:"flex",justifyContent:"flex-end",alignItems:"flex-end"},m(u.Z.REQUEST_INVOICE)&&f.createElement(i.Z,{variant:"contained",color:"primary",disabled:!(pe.length>0),onClick:function(){var e,t;(e={tripIds:pe},t="".concat(Z,"/requestInvoice?").concat(y.stringify(e)),E(t)).then((function(e){0===e.resultCode?(_e(e.invoice.invoiceId),Le(!0),setTimeout((function(){Le(!1)}),5e3),fe([]),Ge()):Ye(e.resultMessage)})).catch((function(e){Ye(e)}))}},b("requestInvoice"))),f.createElement(s.Z,{container:!0,style:{height:600,width:"100%"}},f.createElement(A,(0,r.Z)({totalCount:Ze,trips:ce,loading:he,setTripsCount:X,setTotalFare:le,selectedTrips:pe,setSelectedTrips:fe,totalFare:oe,pendingTrips:Ie,pendingTripCount:xe,pendingTripFare:De},$e))),f.createElement(ae,{setDialogOpen:Le,dialogOpen:He,invoiceId:Be}))};var le=a(4837),ie=a(552);(0,g.Z)((function(e){return(0,p.Z)({})}));const se=function(e){var t=e.invoices,a=e.loading,r=e.totalCount,n=(0,L.Z)(e,["invoices","loading","totalCount"]),o=(0,v.useTranslation)().t,l=n.page,s=n.perPage,c=n.onPageChange,g=n.onPageSizeChange,p=n.onOrderByChange,m=n.onOrderChange,h=(0,re.useHistory)(),b=d()().checkPermission,E=f.useCallback((function(e){e.sortModel[0].sort&&(p(e.sortModel[0].field),m(e.sortModel[0].sort))}),[]),y=[{field:"invoiceId",headerName:o("invoiceId"),sortable:!0,filterable:!0,width:130},{field:"vendor",headerName:o("vendor"),sortable:!0,filterable:!1,width:150},{field:"totalTrips",headerName:o("totalTrips"),sortable:!0,filterable:!0,width:130},{field:"createdDate",headerName:o("createdDate"),sortable:!0,filterable:!0,width:200,renderCell:function(e){return f.createElement("div",null,e.getValue("createdDate")?(0,Q.Z)((0,Y.Z)(new Date(e.getValue("createdDate")).toISOString()),"yyyy-MM-dd HH:mm:ss"):"")}},{field:"tripFare",headerName:o("tripFare"),sortable:!0,filterable:!1,width:120},{field:"paid",headerName:o("paid"),sortable:!0,filterable:!1,width:100},{field:"status",headerName:o("status"),sortable:!1,filterable:!1,width:130,renderCell:function(e){return"partially resolved"===e.row.status?f.createElement(I.Z,{variant:"body2",style:{color:"#8B8000"}},o("partiallyResolved")):"resolved"===e.row.status?f.createElement(I.Z,{variant:"body2",style:{color:"green"}},o("resolved")):f.createElement(I.Z,{variant:"body2",style:{color:"#F56E16"}},o("pending"))}},{field:"actions",headerName:o("actions"),sortable:!1,filterable:!1,renderCell:function(e){return f.createElement(le.Z,{size:"small",color:"primary",variant:"text"},b(u.Z.VIEW_INVOICE_BY_ID)&&f.createElement(i.Z,{onClick:function(){var t;h.push("/home/logistics/settlement/view/".concat(null==e||null===(t=e.row)||void 0===t?void 0:t.id))}},f.createElement(ie.Z,{fontSize:"small"})))},width:100}];return f.createElement(U(),{rows:t,getRowId:function(e){return e.invoiceId},columns:y,rowCount:r,onPageSizeChange:function(e){g(e.pageSize)},onPageChange:function(e){c(e.page)},pagination:!0,paginationMode:"server",page:l,pageSize:s,loading:a,sortingMode:"server",onSortModelChange:E})};var ce=(0,g.Z)((function(e){return(0,p.Z)({rowSpace:{marginTop:e.spacing(3),marginBottom:e.spacing(2)},button:{marginTop:e.spacing(3)}})}));const de=function(){var e,t,a=ce(),l=(0,re.useHistory)(),i=(0,v.useTranslation)().t,c=f.useState([]),d=(0,o.Z)(c,2),u=d[0],g=d[1],p=f.useState(""),m=(0,o.Z)(p,2),b=m[0],Z=m[1],w=f.useState([{key:i("SelectVendor"),value:""}]),I=(0,o.Z)(w,2),T=I[0],N=I[1],k=f.useState(""),D=(0,o.Z)(k,2),M=D[0],F=D[1],P=f.useState(""),z=(0,o.Z)(P,2),B=z[0],_=z[1],R=f.useState(!1),W=(0,o.Z)(R,2),L=W[0],q=W[1],U=f.useState(0),Q=(0,o.Z)(U,2),Y=Q[0],K=Q[1],A=x(),$=null===(e=A.info)||void 0===e||null===(t=e.resellerData)||void 0===t?void 0:t.resellerId,G="3pl_agents"===A.resellerType,J=O().showErrorMessage,X=(0,j.useServerTable)({page:0,perPage:20,order:"desc",orderBy:"",filter:{}});return f.useEffect((function(){G||S("operator").then((function(e){var t=e.resellers.map((function(e){return{key:e.resellerData.resellerName,value:e.resellerData.resellerName+"--"+e.resellerData.resellerId}}));N([{key:i("SelectVendor"),value:""}].concat((0,n.Z)(t)))})).catch((function(e){J(e)}))}),[]),f.useEffect((function(){q(!0);var e,t,a=G||b.split("--")[1];(e={vendor:G?$:a,fromDate:M,toDate:B,pageNumber:X.page,pageSize:X.perPage,sortField:X.orderBy,sortOrder:X.order},t="".concat(C,"/invoice?").concat(y.stringify(e)),E(t)).then((function(e){0==e.resultCode?(K(e.totalInvoices),g(e.invoices)):(g([]),J(e.resultMessage))})).catch((function(e){J(e)})),q(!1)}),[b,M,B,X.perPage,X.page,X.order,X.orderBy]),f.createElement(f.Fragment,null,f.createElement(s.Z,{container:!0,spacing:2},f.createElement(s.Z,{item:!0,xs:12,md:9},f.createElement(H,{isVendor:G,vendorList:T,vendor:b,setVendor:Z,fromDate:M,toDate:B,setFromDate:F,setToDate:_})),f.createElement(s.Z,{item:!0,xs:12,md:3},f.createElement(V.SeamlessButton,{type:"submit",onClick:function(){l.push("/home/logistics/settlement/list")},className:(0,h.default)(a.button),color:"secondary",variant:"outlined"},i("resetSearch")))),f.createElement(s.Z,{container:!0,style:{height:600,width:"100%"}},f.createElement(se,(0,r.Z)({invoices:u,loading:L},X,{totalCount:Y}))))};var ue=a(40),ge=a(975),pe=a(9804);const fe=function(e){var t=e.trips,a=e.loading,r=e.totalTrips,n=(0,L.Z)(e,["trips","loading","totalTrips"]),o=(0,v.useTranslation)().t,l=n.page,i=n.perPage,s=n.onPageChange,c=n.onPageSizeChange,u=n.onOrderByChange,g=n.onOrderChange,p=(d()().checkPermission,f.useCallback((function(e){e.sortModel[0].sort&&(u(e.sortModel[0].field),g(e.sortModel[0].sort))}),[])),m=[{field:"tripId",headerName:o("tripId"),sortable:!0,filterable:!1,width:120},{field:"totalOrdersProcessed",headerName:o("totalOrders"),sortable:!0,filterable:!1,width:120},{field:"totalTripCapacity",headerName:o("totalTripCapacity"),sortable:!0,filterable:!1,width:140},{field:"createdDate",headerName:o("createdDate"),sortable:!0,filterable:!1,width:140},{field:"tripFare",headerName:o("tripFare"),sortable:!0,filterable:!1,width:110},{field:"logisticType",headerName:o("logisticType"),sortable:!0,filterable:!1,width:130},{field:"agentId",headerName:o("agentId"),sortable:!0,filterable:!1,width:130},{field:"priority",headerName:o("priority"),sortable:!0,filterable:!1,width:100},{field:"selfLoad",headerName:o("selfLoad"),sortable:!0,filterable:!1,width:100,flex:1}];return f.createElement(U(),{rows:t,getRowId:function(e){return e.tripId},columns:m,rowCount:r,onPageSizeChange:function(e){c(e.pageSize)},onPageChange:function(e){s(e.page)},pagination:!0,paginationMode:"server",page:l,pageSize:i,loading:a,sortingMode:"server",onSortModelChange:p})};var me=(0,g.Z)((function(e){return(0,p.Z)({stat:{margin:e.spacing(1)},icon:{width:300},rowSpace:{marginTop:e.spacing(3),marginBottom:e.spacing(2)},box:{backgroundColor:"rgb(245, 245, 245)!important"},icons:{fontSize:60,color:"white",borderRadius:10},tripsIcon:{backgroundColor:"#ff5722"},costIcon:{backgroundColor:"#088F8F"},periodIcon:{backgroundColor:"#ff0081"},pendingIcon:{backgroundColor:"#654321"},resolvedIcon:{backgroundColor:"green"},partialIcon:{backgroundColor:"#808000"},title:{marginTop:e.spacing(0)}})}));const ve=function(){var e,t,a=me(),n=(0,re.useParams)().id,i=(0,v.useTranslation)().t,c=f.useState({}),d=(0,o.Z)(c,2),u=d[0],g=d[1],p=f.useState([]),m=(0,o.Z)(p,2),b=m[0],Z=m[1],S=f.useState(""),w=(0,o.Z)(S,2),I=w[0],T=w[1],k=f.useState(!1),P=(0,o.Z)(k,2),x=P[0],z=P[1],B=O().showErrorMessage,_=(0,j.useServerTable)({page:0,perPage:20,order:"desc",orderBy:"",filter:{}});return f.useEffect((function(){var e,t,a;z(!0),(e=n,t={pageNumber:_.page,pageSize:_.perPage,sortField:_.orderBy,sortOrder:_.order},a="".concat(C,"/invoice/").concat(e,"?").concat(y.stringify(t)),E(a)).then((function(e){var t,a;0==e.resultCode?(g(e.invoice),Z(e.invoice.trips),"pending"===(null==e||null===(t=e.invoice)||void 0===t?void 0:t.status)?T(i("pending")):"partially resolved"===(null==e||null===(a=e.invoice)||void 0===a?void 0:a.status)?T(i("partiallyResolved")):T(i("resolved"))):B(e.resultMessage)})).catch((function(e){B(e)})),z(!1)}),[_.perPage,_.page,_.order,_.orderBy]),f.createElement(f.Fragment,null,f.createElement(V.SeamlessContainer,{title:i("invoiceId")+" : "+n,description:i("vendor")+" : "+(null==u?void 0:u.vendor)},f.createElement(s.Z,{container:!0,className:(0,h.default)(a.rowSpace,a.box)},f.createElement(l.Z,{display:"flex",flexDirection:"row",flexWrap:"wrap",justifyContent:"space-evenly"},f.createElement(N,{className:a.stat,label:i("status"),value:I},"resolved"===(null==u?void 0:u.status)&&f.createElement(ue.Z,{className:(0,h.default)(a.icons,a.resolvedIcon),style:{color:"white",fontSize:60}}),"partially resolved"===(null==u?void 0:u.status)&&f.createElement(ge.Z,{className:(0,h.default)(a.icons,a.partialIcon),style:{color:"white",fontSize:60}}),"pending"===(null==u?void 0:u.status)&&f.createElement(pe.Z,{className:(0,h.default)(a.icons,a.pendingIcon),style:{color:"white",fontSize:60}})),f.createElement(N,{className:a.stat,label:i("totalFare"),value:null==u?void 0:u.tripFare},f.createElement(M.Z,{className:(0,h.default)(a.icons,a.costIcon),style:{borderRadius:10,color:"white",fontSize:60}})),f.createElement(N,{className:a.stat,label:i("period"),value:(null==u||null===(e=u.fromDate)||void 0===e?void 0:e.slice(0,10))+"..."+(null==u||null===(t=u.toDate)||void 0===t?void 0:t.slice(0,10))},f.createElement(F.Z,{className:(0,h.default)(a.icons,a.periodIcon),style:{color:"white",fontSize:60}})),f.createElement(N,{className:(0,h.default)(a.stat,a.icon),label:i("noOfTrips"),value:null==u?void 0:u.totalTrips,gridClass:a.title},f.createElement(D.Z,{className:(0,h.default)(a.icons,a.tripsIcon),style:{color:"white",fontSize:60}})))),f.createElement(s.Z,{container:!0,style:{height:600,width:"100%"}},f.createElement(fe,(0,r.Z)({trips:b,loading:x},_,{totalTrips:null==u?void 0:u.totalTrips})))))}},7801:(e,t,a)=>{"use strict";a.d(t,{Z:()=>r});const r={VIEW_TRIPS:"VIEW_TRIPS",VIEW_INVOICES:"VIEW_INVOICES",REQUEST_INVOICE:"REQUEST_INVOICE",VIEW_INVOICE_BY_ID:"VIEW_INVOICE_BY_ID"}}}]);