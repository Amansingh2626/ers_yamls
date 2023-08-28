(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[7719],{17719:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>C});var r=n(11746),l=n(74315),a=n(12783),s=n(2411),o=n.n(s),i=n(6277),u=n(21602),c=n(13268),d=n(82037),p=n(23664),m=n(75696),v=n(33553),f=n(91111),y=n(13029),S=n(48049),I=n(91787),b=n(74208),g=n(54867),h=n(34859),Z=n(61990),N=n(24235),E=n(63135),k=(0,m.makeStyles)((function(e){return(0,m.createStyles)({element:{margin:e.spacing(1),padding:"".concat(e.spacing(1),"px ").concat(e.spacing(1.5),"px"),borderRadius:e.shape.borderRadius},btnPrimary:{background:e.palette.primary.main,color:e.palette.primary.contrastText},decisionWrapper:{marginLeft:e.spacing(1),padding:"".concat(e.spacing(3),"px ").concat(e.spacing(1),"px")}})})),w=function(e){var t,n,r=e.dispatch,l=e.state,a=e.inventoryReseller,s=(0,u.Z)(),i=null==s?void 0:s.requestedPrincipalId;return"parentInventory"==a&&(i=null==s||null===(t=s.info)||void 0===t||null===(n=t.resellerData)||void 0===n?void 0:n.parentResellerId),o().createElement(Z.Z,{resellerId:i||null,state:l,dispatch:r})};const C=function(e){var t,n,m=e.state,Z=e.dispatch,C=(0,E.useTranslation)().t,D=(0,u.Z)(),M=(0,p.Z)().showErrorMessage,x=(0,s.useState)(!1),P=(0,l.Z)(x,2),R=P[0],_=P[1],q=(0,s.useState)(),B=(0,l.Z)(q,2),F=B[0],H=B[1],z=(0,s.useState)(!1),L=(0,l.Z)(z,2),T=L[0],W=L[1],A=(0,s.useState)(!1),j=(0,l.Z)(A,2),G=j[0],J=j[1],K=o().useState("ownInventory"),O=(0,l.Z)(K,2),Q=O[0],U=O[1],V=(0,s.useState)([{label:C("inventory")},{label:C("selectStock")},{label:C("selectBuyer")},{label:C("previewInfo")}]),X=(0,l.Z)(V,2),Y=X[0],$=X[1],ee=k(),te=(0,S.useHistory)(),ne=null===(t=window.config.order)||void 0===t?void 0:t.employeeSnicCategory,re=(0,g.Z)(null==D?void 0:D.info),le=function(){return re===ne};function ae(e){switch(e){case 0:return le()?G:!(0,N.z3)(m);case 1:return le()?!(0,N.z3)(m):null===m.buyer;case 2:return null===m.buyer;default:return!1}}(0,s.useEffect)((function(){var e=(0,r.Z)(Y);return le()||(e=e.filter((function(e){return e.label!==C("inventory")}))),$(e)}),[D]);var se,oe=(0,f.requestHelper)(m,D,Q);return o().createElement(o().Fragment,null,R&&o().createElement(I.Z,{loading:T,orderMessage:F,handleClose:function(){W(!1),te.push("/home/order/orders")}}),o().createElement(a.SeamlessStepper,{steps:Y,components:{Control:function(e){e.goFirst;var t=e.goNext,n=e.goPrev,r=e.disableFirst,l=e.disableLast,s=e.view;return o().createElement(v.Z,null,o().createElement(a.SeamlessButton,{onClick:n,disabled:r,className:(0,i.default)(ee.element),color:"primary",variant:"contained"},C("previous")),o().createElement(a.SeamlessButton,{className:(0,i.default)(ee.element,ee.btnPrimary),onClick:t,disabled:l||ae(s),color:"primary",variant:"contained"},C("next")),l&&o().createElement(a.SeamlessButton,{className:(0,i.default)(ee.element),onClick:function(){return function(e){var t=(0,f.requestHelper)(m,D,Q);_(!0),W(!0),(0,y.ry)(t).then((function(e){0===e.resultCode?(Z({type:"reset",payload:null}),H({message:e.resultMessage,kind:"success"}),W(!1)):(_(!1),M(e))})).catch((function(e){_(!1),M(e)})).finally((function(){W(!1)}))}()},color:"primary",variant:"contained"},C("submit")))}}},le()&&o().createElement(v.Z,{className:ee.decisionWrapper},o().createElement(h.Z,{options:[{label:C("ownInventory"),value:"ownInventory"},{label:C("parentInventory"),value:"parentInventory"}],value:Q,onSelect:function(e){J(!1),U(e)}})),o().createElement(w,{dispatch:Z,state:m,inventoryReseller:Q}),o().createElement(b.Z,{dispatch:Z,state:m,loadData:function(e){return(0,d.yS)(D.requestedPrincipalId.toString(),e)},selectedItems:null!=m&&null!==(n=m.buyer)&&void 0!==n&&n.resellerId?[m.buyer.resellerId]:[],onSelect:function(e){Z({type:"buyer_select",payload:e})},hidden:!1}),o().createElement(c.Z,{dataAPI:oe,state:m,from:function(e,t){var n,r,l,a,s={resellerId:null==e?void 0:e.userId,resellerMSISDN:null==e?void 0:e.targetMSISDN,resellerName:null==e?void 0:e.targetPrincipalName};return"parentInventory"==t&&(s={resellerId:null==e||null===(n=e.info)||void 0===n||null===(r=n.resellerData)||void 0===r?void 0:r.parentResellerId,resellerMSISDN:"",resellerName:null==e||null===(l=e.info)||void 0===l||null===(a=l.resellerData)||void 0===a?void 0:a.parentResellerName}),s}(D,Q),to:(se=null==m?void 0:m.buyer,{resellerId:null==se?void 0:se.resellerId,resellerMSISDN:null==se?void 0:se.resellerMSISDN,resellerName:null==se?void 0:se.resellerName}),dispatch:Z})))}}}]);