(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[3054],{3054:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>T});var l=r(74315),a=r(2411),n=r.n(a),s=r(12783),i=r(6277),o=r(21602),c=r(33553),d=r(23664),u=r(75696),p=r(4409),m=r(48049),f=r(91787),v=r(13432),h=r(13029),b=r(13268),y=r(63783),S=r(36502),g=r(81264),E=r(68635),I=r(42195),N=r(52495),Z=r(63771),k=r(24235),L=(0,u.makeStyles)((function(e){return{groupLabel:{fontWeight:"bolder"},sectionWrapper:{width:"50%",float:"left",paddingRight:5,paddingLeft:5}}}));const M=function(e){var t,r=e.resellerId,s=(e.disabled,e.dispatch),i=e.state,o=L(),c=(0,a.useState)(),d=(0,l.Z)(c,2),u=d[0],p=d[1],m=(0,a.useState)("serialised"),f=(0,l.Z)(m,2),v=f[0],h=f[1];return(0,a.useEffect)((function(){}),[u]),n().createElement(n().Fragment,null,n().createElement("section",{className:o.sectionWrapper},r&&n().createElement(I.Z,{resellerId:null==r?void 0:r.toString(),onSelect:function(e){return r=(null==(t=e)?void 0:t.productSKU)||null,l=(0,k.NO)(i.products,r),p(t),void h(l);var t,r,l},orderType:i.orderType,dispatch:s})),"trackable-non-serialised"==v?n().createElement(Z.Z,{resellerId:null==r?void 0:r.toString(),dispatch:s,state:i,selectedProduct:u}):n().createElement(N.Z,{resellerId:null==r?void 0:r.toString(),dispatch:s,state:i,selectedProduct:u,inventoryReseller:null==i||null===(t=i.seller)||void 0===t?void 0:t.resellerId}))};var P=r(63135),w=function(e){var t,r=e.state,l=e.dispatch,a=null!==(t=r.seller)&&void 0!==t&&t.resellerId?[r.seller.resellerId]:[];return n().createElement(v.Z,{onSelectReseller:function(e){l({type:"seller_select",payload:e})},selectedItems:a})},C=function(e){var t=e.state,r=e.dispatch;return(0,a.useEffect)((function(){r({type:"drop_location_select",payload:[t.buyer]})}),[]),n().createElement(S.Z,{state:t,dispatch:r})},D=function(e){var t=e.state,r=e.dispatch;return n().createElement(g.Z,{state:t,dispatch:r})},_=function(e){var t,r=e.dispatch,l=e.state;return n().createElement(M,{resellerId:(null==l||null===(t=l.seller)||void 0===t?void 0:t.resellerId)||null,state:l,dispatch:r})},x=function(e){var t=e.dispatch,r=e.state;return n().createElement(E.Z,{state:r,dispatch:t})},R=(0,u.makeStyles)((function(e){return(0,u.createStyles)({element:{margin:e.spacing(1),padding:"".concat(e.spacing(1),"px ").concat(e.spacing(1.5),"px"),borderRadius:e.shape.borderRadius},backdrop:{zIndex:e.zIndex.drawer+1,color:"#fff"},btnPrimary:{background:e.palette.primary.main,color:e.palette.primary.contrastText}})}));const T=function(e){var t=e.state,r=e.dispatch,u=(0,o.Z)(),v=(0,d.Z)(),S=v.showSuccessMessage,g=v.showErrorMessage,E=R(),I=(0,a.useState)(!1),N=(0,l.Z)(I,2),Z=N[0],L=N[1],M=(0,a.useState)(),T=(0,l.Z)(M,2),F=T[0],H=T[1],q=(0,a.useState)(!1),z=(0,l.Z)(q,2),B=z[0],O=z[1],W=(0,a.useState)(!1),A=(0,l.Z)(W,2),U=A[0],K=A[1],j=(0,m.useHistory)(),G=(0,P.useTranslation)().t;(0,a.useEffect)((function(){r({type:"buyer_select",payload:{resellerId:null==u?void 0:u.requestedPrincipalId,resellerMSISDN:null==u?void 0:u.targetMSISDN,resellerName:null==u?void 0:u.targetPrincipalName}})}),[u]);var J=[{label:G("selectSeller")},{label:G("dropLocation")},{label:G("pickUpLocation")},{label:G("selectStock")},{label:G("deliveryOption")},{label:G("paymentInformation")},{label:G("orderDetails")}];function Q(){L(!0);var e=(0,y.requestHelper)(t);return K(!0),(0,h.ry)(e).then((function(e){0===e.resultCode?(S(e),H({message:e.resultMessage,kind:"success"}),K(!1)):(L(!1),g(e))})).catch((function(e){L(!1),g(e)}))}function V(e){switch(e){case 0:return null==t.seller;case 1:return 0===t.dropLocation.length;case 2:return 0===t.pickupLocation.length;case 3:return!(0,k.z3)(t);case 4:return!t.deliveryOptions.mode;case 5:return!(t.paymentAgreement&&t.paymentMode);default:return!1}}var X,Y,$=(0,y.requestHelper)(t);return n().createElement(n().Fragment,null,Z&&n().createElement(f.Z,{loading:U,orderMessage:F,handleClose:function(){L(!1),r({type:"reset",payload:null}),j.push("/home/order/orders")}}),n().createElement(s.SeamlessStepper,{steps:t.buyer&&"subscriber"===t.buyer.resellerTypeName?J:J.filter((function(e){return e.label!==G("additionalInfo")})),components:{Control:function(e){e.goFirst;var t=e.goNext,r=e.goPrev,l=e.disableFirst,a=e.disableLast,o=e.view;return n().createElement(c.Z,null,n().createElement(s.SeamlessButton,{onClick:r,disabled:l,className:(0,i.default)(E.element,E.btnPrimary),color:"primary",variant:"contained"},G("previous")),n().createElement(s.SeamlessButton,{className:(0,i.default)(E.element,E.btnPrimary),onClick:t,disabled:a||V(o),color:"primary",variant:"contained"},G("next")),a&&n().createElement(s.SeamlessButton,{className:(0,i.default)(E.element),onClick:Q,color:"primary",variant:"contained",disabled:B},G("submit")))}}},n().createElement(w,{dispatch:r,state:t}),n().createElement(C,{dispatch:r,state:t}),n().createElement(D,{dispatch:r,state:t}),n().createElement(_,{dispatch:r,state:t}),n().createElement(x,{dispatch:r,state:t}),n().createElement(p.Z,{state:t,dispatch:r}),n().createElement(b.Z,{dataAPI:$,paymentInfo:!0,state:t,from:(Y=null==t?void 0:t.seller,{resellerId:null==Y?void 0:Y.resellerId,resellerMSISDN:null==Y?void 0:Y.resellerMSISDN,resellerName:null==Y?void 0:Y.resellerName,pickupLocation:null==t?void 0:t.pickupLocation}),to:(X=null==t?void 0:t.buyer,{resellerId:null==X?void 0:X.resellerId,resellerMSISDN:null==X?void 0:X.resellerMSISDN,resellerName:null==X?void 0:X.resellerName,dropLocation:null==t?void 0:t.dropLocation}),itemList:(0,k.RH)(t),dispatch:r,setDisableSubmit:O})))}}}]);