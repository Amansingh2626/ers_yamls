(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[7895],{17895:(e,n,t)=>{"use strict";t.r(n),t.d(n,{requestHelper:()=>u});var i=t(33822),r=function(e){return e.stockQuantity.map((function(e){return{productSku:e.productSKU,quantity:1,data:{amount:parseInt(e.quantity,10)}}}))};function u(e){var n,t,u,d={id:null==e||null===(n=e.initiator)||void 0===n?void 0:n.resellerId,additionalFields:[]},a={id:null==e||null===(t=e.seller)||void 0===t?void 0:t.resellerId,additionalFields:[]},l={id:null==e||null===(u=e.buyer)||void 0===u?void 0:u.resellerId,additionalFields:[]},o=new i.Z({state:e});return o.buyer=l,o.seller=d,o.sender=a,o.receivers=[l],o.items=r(e),o.additionalFields=function(e){return e.additionalFields.map((function(e){return{name:e.key,value:e.value}})).concat({name:"invoiceAmount",value:r(e).reduce((function(e,n){return e+n.data.amount}),0).toString()})}(e),o.paymentAgreement="NA",o.paymentMode="NO_PAYMENT_REQD",o.orderType="ISO_DST",o.requestParams}}}]);