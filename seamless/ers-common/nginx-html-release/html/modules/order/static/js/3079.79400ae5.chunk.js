(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[3079],{43079:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>O});var l=r(74315),n=r(12783),a=r(2411),s=r.n(a),i=r(6277),o=r(8629),u=r(49429),c=r(38724),d=r(79249),m=r(12231),p=r(73698),f=r(86619),v=r(33553),h=r(21602),S=r(40135),y=r(75696),g=r(13029),E=r(23664),I=r(97987),b=r(27115),Z=r(79650),k=r(48049),N=r(13278),C=r(63135),w=(0,y.makeStyles)((function(e){return(0,y.createStyles)({element:{margin:e.spacing(1)},heightAuto:{height:"auto"}})})),M=function(e){var t=e.dispatch,r=e.state,l=(0,h.Z)();return s().createElement(b.Z,{resellerId:null==l?void 0:l.requestedPrincipalId,state:r,dispatch:t})};const O=function(e){var t,r=e.state,y=e.dispatch,b=w(),O=(0,h.Z)(),T=(0,a.useState)(!1),P=(0,l.Z)(T,2),q=P[0],B=P[1],D=(0,a.useState)([]),x=(0,l.Z)(D,2),A=x[0],F=x[1],H=(0,a.useState)(),L=(0,l.Z)(H,2),R=L[0],W=L[1],_=(0,a.useState)(),K=(0,l.Z)(_,2),U=K[0],z=K[1],Q=(0,a.useState)([]),j=(0,l.Z)(Q,2),G=(j[0],j[1],(0,a.useState)(["PO","SO","ISO","IPO"])),J=(0,l.Z)(G,2),V=J[0],X=(J[1],(0,E.Z)()),Y=X.showSuccessMessage,$=X.showErrorMessage,ee=X.showInfoMessage,te=(0,C.useTranslation)().t,re=(0,k.useHistory)();function le(){B(!1),W(null),z("")}(0,a.useEffect)((function(){(0,g.an)().then((function(e){F(e.orderReasons)})).catch((function(e){ee(te("unableToLoadReturnReasonList"))}))}),[]);var ne=[{label:te("orderSelect")},{label:te("inventory")},{label:te("creditNoteDetailsAndSubmit")}];function ae(e){switch(e){case 0:return null===r.returnOrder;case 1:return!(0,Z.z)(r);default:return!1}}var se,ie,oe=(0,I.requestHelper)(r,U);return s().createElement(s().Fragment,null,s().createElement(o.Z,{open:q,onClose:le,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description",fullWidth:!0,maxWidth:"sm",disableBackdropClick:!0},s().createElement(d.Z,{id:"alert-dialog-title"},"Please specifiy the reason of return"),s().createElement(c.Z,null,s().createElement(p.Z,{style:{marginTop:20},labelId:"demo-simple-select-label",id:"demo-simple-select",value:R,onChange:function(e){z("");var t=e.target.value;W(t);var r=A.find((function(e){return e.code===t}));z(r.description)},fullWidth:!0},A.map((function(e){return s().createElement(m.Z,{key:e.code,value:e.code},e.code)}))),"OTHER"===R&&s().createElement(f.Z,{fullWidth:!0,multiline:!0,rowsMax:5,rows:5,value:U,style:{marginTop:30},placeholder:"Specify other reasons",onChange:function(e){e.target.value.length>=400||z(e.target.value)}})),s().createElement(u.Z,null,s().createElement(n.SeamlessButton,{onClick:le,color:"primary"},te("cancel")),s().createElement(n.SeamlessButton,{disabled:!U,onClick:function(){var e=(0,I.requestHelper)(r,U);(0,g.ry)(e).then((function(e){0===e.resultCode?(Y(e),y({type:"reset",payload:null}),re.push("/home/order/orders/return")):(le(),$(e))})).catch((function(e){le(),$(e)})).finally((function(){le()}))},color:"primary",variant:"contained",autoFocus:!0},te("return")))),s().createElement(n.SeamlessStepper,{className:b.heightAuto,steps:ne,components:{Control:function(e){e.goFirst;var t=e.goNext,r=e.goPrev,l=e.disableFirst,a=e.disableLast,o=e.view;return s().createElement(v.Z,{style:{marginTop:25}},s().createElement(n.SeamlessButton,{onClick:r,disabled:l,className:(0,i.default)(b.element),color:"primary",variant:"contained"},te("previous")),s().createElement(n.SeamlessButton,{className:(0,i.default)(b.element),onClick:t,disabled:a||ae(o),color:"primary",variant:"contained"},te("next")),a&&s().createElement(n.SeamlessButton,{className:(0,i.default)(b.element),onClick:function(){B(!0)},color:"primary",variant:"contained"},te("submit")))}}},s().createElement(N.Z,{state:r,dispatch:y,resellerId:O.requestedPrincipalId,resellerType:"senderId",selectedItem:null==r||null===(t=r.returnOrder)||void 0===t?void 0:t.orderId,supportedOrderTypes:V}),s().createElement(M,{dispatch:y,state:r}),s().createElement(S.Z,{dataAPI:oe,paymentInfo:!0,state:r,from:(ie=r.receiver,{resellerName:(null==ie?void 0:ie.resellerName)||"",resellerId:(null==ie?void 0:ie.resellerId)||"",resellerMSISDN:(null==ie?void 0:ie.resellerMSISDN)||""}),to:(se=r.sender,{resellerName:(null==se?void 0:se.resellerName)||"",resellerId:(null==se?void 0:se.resellerId)||"",resellerMSISDN:(null==se?void 0:se.resellerMSISDN)||""}),itemList:r.stockQuantity.map((function(e){var t;return{productSKU:null==e?void 0:e.productSKU,quantity:null==e?void 0:e.quantity,price:null==e||null===(t=e.price)||void 0===t?void 0:t.price}})),dispatch:y})))}}}]);