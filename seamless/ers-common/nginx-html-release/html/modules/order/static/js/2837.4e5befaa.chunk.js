(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[2837],{72837:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>ee});var a=n(74315),r=n(12783),l=n(2411),i=n.n(l),o=n(6277),c=n(8629),s=n(49429),u=n(38724),d=n(79249),p=n(12231),m=n(73698),f=n(86619),h=n(33553),v=n(21602),g=n(40135),y=n(75696),E=n(13029),Z=n(23664),S=n(24275),b=n(27115),k=n(73313),I=n(76203),N=n(79650),w=n(48049),C=n(13278),x=n(11746),T=n(79692),B=n(12420),R=n(74603),O=n(85256),q=n(77277),P=n(42713),D=n(36137),L=n(37390),M=n(97485),F=n(15584),_=n(89571),W=n(90436),z=n(67358),A=n(61715),H=n(98480),U=n(63135),K=(0,T.Z)((function(e){return(0,B.Z)({wrapper:{padding:"2px 4px",display:"flex",alignItems:"center",borderRadius:2,borderBottom:"1px solid #cac3c3"},section:{margin:e.spacing(1),marginTop:e.spacing(2),marginBottom:e.spacing(2)},input:{marginLeft:e.spacing(1),flex:1},iconButton:{padding:10},divider:{height:28,margin:4},btnSpacing:{marginTop:e.spacing(2),marginBottom:e.spacing(2)}})}));function Q(e){var t=e.data,n=e.onRemove;return i().createElement(L.Z,null,i().createElement(M.Z,null,t&&t.map((function(e){return i().createElement(_.Z,{hover:!0,key:e.key},i().createElement(F.Z,{component:"th",scope:"row",style:{width:"35%"}},e.key),i().createElement(F.Z,null,i().createElement("img",{src:e.value,style:{maxWidth:200}})),i().createElement(F.Z,{size:"small",style:{width:50,padding:0}},i().createElement(q.Z,{onClick:function(){return n(e.key)},size:"small"},i().createElement(P.Z,{fontSize:"small"}))))}))))}function j(e){var t=e.data,n=e.setData,r=(e.state,e.dispatch,K()),o=(0,v.Z)(),c=(0,U.useTranslation)().t,s=(0,Z.Z)().showErrorMessage,u=(0,l.useState)(),d=(0,a.Z)(u,2),p=d[0],m=d[1],f=(0,l.useState)(),g=(0,a.Z)(f,2),y=(g[0],g[1]),E=(0,l.useState)(),S=(0,a.Z)(E,2),b=S[0],k=S[1],I=(0,l.useState)(!1),N=(0,a.Z)(I,2),w=N[0],C=N[1],T=(0,l.useState)("file"),B=(0,a.Z)(T,2),P=B[0],L=B[1],M=i().useRef(null);return i().createElement("section",{className:r.section},i().createElement(W.Z,{variant:"body1",color:"primary"},c("uploadPhotosOfReturningItems")),w&&i().createElement(z.Z,{style:{width:"100%"}}),i().createElement(h.Z,{className:r.wrapper},i().createElement(R.Z,{className:r.input,placeholder:c("title"),value:b,onChange:function(e){return k(e.target.value)}}),i().createElement(O.Z,{className:r.divider,orientation:"vertical"}),i().createElement("input",{accept:"image/*",className:r.input,type:"file",placeholder:c("chooseFile"),onClick:function(e){return e.target.value=null},onChange:function(e){return m(e.target.files[0])},ref:M}),i().createElement(q.Z,{color:"primary",className:r.iconButton,"aria-label":"directions",endIcon:i().createElement(D.Z,null),onClick:function(){b&&"file"===P&&(C(!0),function(e,t){var n="inventory",a=new FormData;return a.append("files",e,e.name),a.append("uploadedBy",t),a.append("uploadedFor",t),a.append("resourceOwner",t),a.append("data","{}"),(0,A.cT)(a,n).then((function(e){return e.resourceData[0].resourceId})).then((function(e){return(0,H.dk)(n,e)})).catch((function(e){return s(e)})).finally((function(){return C(!1)}))}(p,o.requestedPrincipalId).then((function(e){return function(e,t,a,r){var l=e.findIndex((function(e){return e.key===t}));-1!==l?e[l]={key:t,value:a,required:e[l].required||!1,kind:r}:e=e.concat({key:t,value:a,required:!1,kind:r}),n(e),k(""),y(""),L("file"),m(null),M.current.value=null}((0,x.Z)(t),b,e,P)})).then((function(){return C(!1)})))},disabled:!b||!p})),i().createElement(Q,{data:t,onRemove:function(e){var a=t.filter((function(t){return t.key!==e}));n(a)}}))}var G=n(68635),J=(0,y.makeStyles)((function(e){return(0,y.createStyles)({element:{margin:e.spacing(1),background:e.palette.primary.main,color:e.palette.primary.contrastText,padding:"".concat(e.spacing(1),"px ").concat(e.spacing(1.5),"px"),borderRadius:e.shape.borderRadius},heightAuto:{height:"auto"}})})),V=function(e){var t=e.dispatch,n=e.state,a=(0,v.Z)();return i().createElement(b.Z,{resellerId:null==a?void 0:a.requestedPrincipalId,state:n,dispatch:t})},X=function(e){var t=e.state,n=e.dispatch;return i().createElement(k.default,{state:t,dispatch:n})},Y=function(e){var t=e.state,n=e.dispatch;return i().createElement(I.default,{state:t,dispatch:n})},$=function(e){var t=e.dispatch,n=e.state;return i().createElement(G.Z,{state:n,dispatch:t})};const ee=function(e){var t,n=e.state,y=e.dispatch,b=J(),k=(0,v.Z)(),I=(0,l.useState)(!1),x=(0,a.Z)(I,2),T=x[0],B=x[1],R=(0,l.useState)([]),O=(0,a.Z)(R,2),q=O[0],P=O[1],D=(0,l.useState)(),L=(0,a.Z)(D,2),M=L[0],F=L[1],_=(0,l.useState)(),W=(0,a.Z)(_,2),z=W[0],A=W[1],H=(0,l.useState)([]),K=(0,a.Z)(H,2),Q=K[0],G=K[1],ee=(0,l.useState)(["PO","SO"]),te=(0,a.Z)(ee,2),ne=te[0],ae=(te[1],(0,Z.Z)()),re=ae.showSuccessMessage,le=ae.showErrorMessage,ie=ae.showInfoMessage,oe=(0,U.useTranslation)().t,ce=(0,w.useHistory)();function se(){B(!1),F(null),A("")}(0,l.useEffect)((function(){(0,E.an)().then((function(e){P(e.orderReasons)})).catch((function(e){ie(oe("unableToLoadReturnReasonList"))}))}),[]);var ue=[{label:oe("orderSelect")},{label:oe("inventory")},{label:oe("pickUpLocation")},{label:oe("dropLocation")},{label:oe("uploadInventoryPhotos")},{label:oe("deliveryOption")},{label:oe("creditNoteDetailsAndSubmit")}];function de(e){switch(e){case 0:return null===n.returnOrder;case 1:return!(0,N.z)(n);case 2:return 0===n.dropLocation.length;case 3:return 0===n.pickupLocation.length;case 4:return!1;case 5:return!n.deliveryOptions.mode;default:return!1}}var pe,me,fe=(0,S.requestHelper)(n,z);return i().createElement(i().Fragment,null,i().createElement(c.Z,{open:T,onClose:se,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description",fullWidth:!0,maxWidth:"sm",disableBackdropClick:!0},i().createElement(d.Z,{id:"alert-dialog-title"},"Please specifiy the reason of return"),i().createElement(u.Z,null,i().createElement(m.Z,{style:{marginTop:20},labelId:"demo-simple-select-label",id:"demo-simple-select",value:M,onChange:function(e){A("");var t=e.target.value;F(t);var n=q.find((function(e){return e.code===t}));A(n.description)},fullWidth:!0},q.map((function(e){return i().createElement(p.Z,{key:e.code,value:e.code},e.code)}))),"OTHER"===M&&i().createElement(f.Z,{fullWidth:!0,multiline:!0,rowsMax:5,rows:5,value:z,style:{marginTop:30},placeholder:"Specify other reasons",onChange:function(e){e.target.value.length>=400||A(e.target.value)}})),i().createElement(s.Z,null,i().createElement(r.SeamlessButton,{onClick:se,color:"primary"},oe("cancel")),i().createElement(r.SeamlessButton,{disabled:!z,onClick:function(){var e=(0,S.requestHelper)(n,z);(0,E.ry)(e).then((function(e){0===e.resultCode?(re(e),y({type:"reset",payload:null}),ce.push("/home/order/orders")):(se(),le(e))})).catch((function(e){se(),le(e)})).finally((function(){se()}))},color:"primary",variant:"contained",autoFocus:!0},oe("return")))),i().createElement(r.SeamlessStepper,{className:b.heightAuto,steps:ue,components:{Control:function(e){e.goFirst;var t=e.goNext,n=e.goPrev,a=e.disableFirst,l=e.disableLast,c=e.view;return i().createElement(h.Z,{style:{marginTop:25}},i().createElement(r.SeamlessButton,{onClick:n,disabled:a,className:(0,o.default)(b.element),color:"primary",variant:"contained"},oe("previous")),i().createElement(r.SeamlessButton,{className:(0,o.default)(b.element),onClick:function(){return function(e,t){switch(e){case 4:y({type:"set_addtional_info",payload:Q.map((function(e,t){return{key:"Photo-RO-".concat(t+1,":").concat(e.key),value:e.value}}))});default:return t()}}(c,t)},disabled:l||de(c),color:"primary",variant:"contained"},oe("next")),l&&i().createElement(r.SeamlessButton,{className:(0,o.default)(b.element),onClick:function(){B(!0)},color:"primary",variant:"contained"},oe("submit")))}}},i().createElement(C.Z,{state:n,dispatch:y,resellerId:k.requestedPrincipalId,resellerType:"buyerId",selectedItem:null==n||null===(t=n.returnOrder)||void 0===t?void 0:t.orderId,supportedOrderTypes:ne}),i().createElement(V,{dispatch:y,state:n}),i().createElement(X,{dispatch:y,state:n}),i().createElement(Y,{dispatch:y,state:n}),i().createElement(j,{data:Q,setData:G,state:n,dispatch:y}),i().createElement($,{dispatch:y,state:n}),i().createElement(g.Z,{dataAPI:fe,paymentInfo:!0,state:n,from:(me=n.receiver,{resellerName:(null==me?void 0:me.resellerName)||"",resellerId:(null==me?void 0:me.resellerId)||"",resellerMSISDN:(null==me?void 0:me.resellerMSISDN)||""}),to:(pe=n.sender,{resellerName:(null==pe?void 0:pe.resellerName)||"",resellerId:(null==pe?void 0:pe.resellerId)||"",resellerMSISDN:(null==pe?void 0:pe.resellerMSISDN)||""}),itemList:n.stockQuantity.map((function(e){var t;return{productSKU:null==e?void 0:e.productSKU,quantity:null==e?void 0:e.quantity,price:null==e||null===(t=e.price)||void 0===t?void 0:t.price}})),dispatch:y})))}}}]);