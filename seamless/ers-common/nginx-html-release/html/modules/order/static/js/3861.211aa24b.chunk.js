(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[3861],{3861:(e,r,t)=>{"use strict";t.r(r),t.d(r,{default:()=>T});var l=t(74315),n=t(2411),a=t.n(n),o=t(12783),s=t(6277),i=t(23664),u=t(8629),c=t(49429),d=t(38724),m=t(79249),p=t(12231),v=t(73698),f=t(86619),S=t(21602),y=t(82037),I=t(33553),g=t(75696),h=t(13029),b=t(15670),E=t(28120),N=t(13268),Z=t(48049),C=t(63135),k=t(74208),B=(0,g.makeStyles)((function(e){return(0,g.createStyles)({element:{margin:e.spacing(1)}})}));const T=function(e){var r,t,g,T=e.state,w=e.dispatch,M=B(),R=(0,S.Z)(),A=(0,n.useState)(!1),P=(0,l.Z)(A,2),F=P[0],q=P[1],D=(0,n.useState)([]),H=(0,l.Z)(D,2),O=H[0],_=H[1],x=(0,n.useState)(),L=(0,l.Z)(x,2),W=L[0],j=L[1],G=(0,n.useState)(),U=(0,l.Z)(G,2),K=U[0],z=U[1],J=(0,n.useState)(!1),Q=(0,l.Z)(J,2),V=Q[0],X=Q[1],Y=(0,i.Z)(),$=Y.showSuccessMessage,ee=Y.showErrorMessage,re=Y.showInfoMessage,te=((0,C.useTranslation)().t,(0,Z.useHistory)());function le(){q(!1),j(null),z("")}function ne(e){switch(e){case 0:return null===T.recoverFrom;case 1:return null===T.returnOrder;default:return!1}}(0,n.useEffect)((function(){w({type:"seller_select",payload:{resellerId:null==R?void 0:R.requestedPrincipalId,resellerMSISDN:null==R?void 0:R.targetMSISDN,resellerName:null==R?void 0:R.targetPrincipalName}})}),[R]),(0,n.useEffect)((function(){(0,h.an)().then((function(e){_(e.orderReasons)})).catch((function(e){console.error(e),re("Unable to load return reason list")}))}),[]);var ae,oe,se=(0,b.requestHelper)(T,K);return a().createElement(a().Fragment,null,a().createElement(u.Z,{open:F,onClose:le,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description",fullWidth:!0,maxWidth:"sm",disableBackdropClick:!0},a().createElement(m.Z,{id:"alert-dialog-title"},"Please specifiy the reason of return"),a().createElement(d.Z,null,a().createElement(v.Z,{style:{marginTop:20},labelId:"demo-simple-select-label",id:"demo-simple-select",value:W,onChange:function(e){z("");var r=e.target.value;j(r);var t=O.find((function(e){return e.code===r}));z(t.description)},fullWidth:!0},O.map((function(e){return a().createElement(p.Z,{key:e.code,value:e.code},e.code)}))),"OTHER"===W&&a().createElement(f.Z,{fullWidth:!0,multiline:!0,rowsMax:5,rows:5,value:K,style:{marginTop:30},placeholder:"Specify other reasons",onChange:function(e){e.target.value.length>=400||z(e.target.value)}})),a().createElement(c.Z,null,a().createElement(o.SeamlessButton,{onClick:le,color:"primary"},"Cancel"),a().createElement(o.SeamlessButton,{disabled:!K||V,onClick:function(){X(!0);var e=(0,b.requestHelper)(T,K);(0,h.ry)(e).then((function(e){0===e.resultCode?(le(),$(e),w({type:"reset",payload:null}),te.push("/home/order/orders")):(le(),ee(e),X(!1))})).catch((function(e){le(),ee(e),X(!1)}))},color:"primary",variant:"contained",autoFocus:!0},V?"Returning ...":"Return"))),a().createElement(o.SeamlessStepper,{steps:[{label:"Recover From"},{label:"Inventory"},{label:"Review & Submit"}],components:{Control:function(e){e.goFirst;var r=e.goNext,t=e.goPrev,l=e.disableFirst,n=e.disableLast,i=e.view;return a().createElement(I.Z,null,a().createElement(o.SeamlessButton,{onClick:t,disabled:l,className:(0,s.default)(M.element),color:"primary",variant:"contained"},"Previous"),a().createElement(o.SeamlessButton,{className:(0,s.default)(M.element),onClick:r,disabled:n||ne(i),color:"primary",variant:"contained"},"Next"),n&&a().createElement(o.SeamlessButton,{className:(0,s.default)(M.element),onClick:function(){q(!0)},color:"primary",variant:"contained"},"Submit"))}}},a().createElement(k.Z,{dispatch:w,state:T,loadData:function(e){return(0,y.yS)(R.requestedPrincipalId.toString(),e)},selectedItems:null!=T&&null!==(r=T.buyer)&&void 0!==r&&r.resellerId?[T.buyer.resellerId]:[],onSelect:function(e){w({type:"recover_from",payload:e})},hidden:!1}),a().createElement(E.Z,{selectedItem:null==T||null===(t=T.returnOrder)||void 0===t?void 0:t.orderId,buyerId:null==T||null===(g=T.recoverFrom)||void 0===g?void 0:g.resellerId,dispatch:w,state:T}),a().createElement(N.Z,{dataAPI:se,state:T,from:(oe=T.recoverFrom,{resellerName:(null==oe?void 0:oe.resellerName)||"",resellerId:(null==oe?void 0:oe.resellerId)||"",resellerMSISDN:(null==oe?void 0:oe.resellerMSISDN)||""}),to:{resellerName:(null==R?void 0:R.targetPrincipalName)||"",resellerId:(null==R?void 0:R.requestedPrincipalId)||"",resellerMSISDN:(null==R?void 0:R.targetMSISDN)||""},itemList:(ae=function(e){return e.batchIds&&Array.isArray(e.batchIds)&&"BATCH"===e.reserveType?e.batchIds.join(", "):"SERIAL"===e.reserveType?"-":"RANGE"===e.reserveType?e.ranges.map((function(e){return e.batchId})).join(", "):"-"},T.returnOrder&&T.returnOrder.orderId?T.returnOrder.items.map((function(e){return{productSKU:e.productSku,quantity:parseInt((r=e,"RANGE"===r.reserveType?r.ranges.reduce((function(e,r){return BigInt(r.endSerial)-BigInt(r.startSerial)+BigInt(e)}),0)+BigInt(1):"SERIAL"===r.reserveType?r.serials.length:"BATCH"===r.reserveType?r.quantity:0).toString(),10),boxId:ae(e)};var r})):[]),dispatch:w})))}}}]);