(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[8120],{28120:(e,t,r)=>{"use strict";r.d(t,{Z:()=>y});var n=r(74315),o=r(2411),l=r.n(o),a=r(86619),d=r(77277),i=r(41744),c=r.n(i),s=r(21853),u=r(63135),f=r(13029),h=r(19985),m=r(23664);const y=function(e){var t,r=e.dispatch,i=e.state,y=e.buyerId,p=e.selectedItem,v=void 0===p?null==i||null===(t=i.returnOrder)||void 0===t?void 0:t.orderId:p,I=(0,o.useState)(),g=(0,n.Z)(I,2),b=g[0],x=g[1],E=(0,o.useState)([]),Z=(0,n.Z)(E,2),w=Z[0],N=Z[1],S=(0,o.useState)(!1),_=(0,n.Z)(S,2),k=_[0],C=_[1],T=(0,u.useTranslation)().t,M=(0,m.Z)().showInfoMessage,F=[{field:"orderId",headerName:T("orderId"),sortable:!0,flex:2},{field:"orderType",headerName:T("orderType"),sortable:!0,flex:1},{field:"paymentMode",headerName:T("paymentMode"),sortable:!0,flex:1},{field:"state",headerName:T("state"),sortable:!0,flex:1},{field:"createTimestamp",headerName:T("createTimestamp"),sortable:!0,flex:1,type:"date"}];return l().createElement("section",{style:{display:"flex",justifyContent:"center",flexDirection:"column"}},l().createElement("section",{style:{alignSelf:"center"}},l().createElement(a.Z,{style:{width:400},label:T("orderId"),value:b,onChange:function(e){x(e.target.value)}}),l().createElement(d.Z,{style:{height:32},onClick:function(){r({type:"order_select_reset",payload:null}),C(!0);var e=(0,h.Er)({orderId:b,receiverId:y});(0,f.HG)("filter=".concat(e)).then((function(e){var t;return 0===e.orders.length?Promise.reject(T("orderNotFound")):(0,f.s$)(null===(t=e.orders[0])||void 0===t?void 0:t.orderId)})).then((function(e){return N([e])})).catch((function(e){M(T("orderNotFound"))})).finally((function(){return C(!1)}))},endIcon:l().createElement(s.Z,{fontSize:"large"})},T("search"))),l().createElement("section",{style:{height:300}},l().createElement(c(),{loading:k,getRowId:function(e){return null==e?void 0:e.orderId},rows:w,columns:F,checkboxSelection:!0,hideFooterPagination:!0,onRowSelected:function(e){r({type:"order_select",payload:e.data})},selectionModel:[v]})))}}}]);