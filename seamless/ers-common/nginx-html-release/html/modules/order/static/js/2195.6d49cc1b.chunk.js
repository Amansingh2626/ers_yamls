(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[2195],{42195:(n,e,i)=>{"use strict";i.d(e,{Z:()=>Z});var l=i(7560),r=i(11746),t=i(74315),o=i(2411),u=i.n(o),d=i(86619),a=i(11637),c=i(77954),v=i(38636),s=i(75696),p=i(43434),f=i(94339),b=i(32552),y=(0,s.makeStyles)((function(n){return{groupLabel:{fontWeight:"bolder"}}}));const Z=function(n){var e=n.resellerId,i=n.onSelect,s=n.disabled,Z=void 0!==s&&s,m=n.dispatch,g=n.orderType,h=(0,o.useState)(),S=(0,t.Z)(h,2),T=S[0],I=S[1],E=y();(0,o.useEffect)((function(){(0,v.$f)(e).then((function(n){return function(n){var e,i,l,t,o,u,d,a,c,v,s,p,f=[],b=window.config;return"ISO"===g&&(f=[].concat((0,r.Z)(f),(0,r.Z)(n.filter((function(n){var e;return"services-b2b"!==(null===(e=n.product)||void 0===e?void 0:e.productType)}))))),null!=b&&null!==(e=b.order)&&void 0!==e&&null!==(i=e.serviceInfo)&&void 0!==i&&null!==(l=i.digitalTypes)&&void 0!==l&&l.includes(g)&&(f=[].concat((0,r.Z)(f),(0,r.Z)(n.filter((function(n){var e,i;return(null===(e=n.product)||void 0===e?void 0:e.productType.includes("services"))&&"services-topup"!==(null===(i=n.product)||void 0===i?void 0:i.productType)}))))),null!=b&&null!==(t=b.order)&&void 0!==t&&null!==(o=t.serviceInfo)&&void 0!==o&&null!==(u=o.serialisedTypes)&&void 0!==u&&u.includes(g)&&(f=[].concat((0,r.Z)(f),(0,r.Z)(n.filter((function(n){var e;return"serialised"===(null===(e=n.product)||void 0===e?void 0:e.productType)}))))),null!=b&&null!==(d=b.order)&&void 0!==d&&null!==(a=d.serviceInfo)&&void 0!==a&&null!==(c=a.nonSerialisedTypes)&&void 0!==c&&c.includes(g)&&(f=[].concat((0,r.Z)(f),(0,r.Z)(n.filter((function(n){var e;return"non-serialised"===(null===(e=n.product)||void 0===e?void 0:e.productType)}))))),null!=b&&null!==(v=b.order)&&void 0!==v&&null!==(s=v.serviceInfo)&&void 0!==s&&null!==(p=s.trackableNonSerialisedTypes)&&void 0!==p&&p.includes(g)&&(f=[].concat((0,r.Z)(f),(0,r.Z)(n.filter((function(n){var e;return"trackable-non-serialised"===(null===(e=n.product)||void 0===e?void 0:e.productType)}))))),f.filter((function(n,e,i){return i.findIndex((function(e){return e.variant.variantId===n.variant.variantId}))===e}))}(n)})).then((function(n){m({type:"product_loaded",payload:n}),I(n)}))}),[e]);var k=(0,a.D)({stringify:function(n){var e,i,l;return(null==n||null===(e=n.variant)||void 0===e?void 0:e.productSKU)+(null==n||null===(i=n.product)||void 0===i?void 0:i.name)+(null==n||null===(l=n.product)||void 0===l?void 0:l.description)}});return u().createElement(c.ZP,{id:"combo-box-demo",options:T||[],disableClearable:!0,disabled:Z,onChange:function(n,e){i(e.variant)},filterOptions:k,getOptionLabel:function(n){var e;return null==n||null===(e=n.variant)||void 0===e?void 0:e.productSKU},renderOption:function(n){return e=n,u().createElement(u().Fragment,null,u().createElement(f.Z,{elevation:0,style:{width:"100%"}},u().createElement(b.Z,{avatar:u().createElement(p.Z,{src:null!==(i=e.variant)&&void 0!==i&&i.imageUrl?null===(l=e.variant)||void 0===l?void 0:l.imageUrl:""}),title:"(".concat(null==e||null===(r=e.variant)||void 0===r?void 0:r.productSKU,") - ").concat(null==e||null===(t=e.product)||void 0===t?void 0:t.name),subheader:null==e||null===(o=e.product)||void 0===o?void 0:o.description}),u().createElement("p",{style:{float:"right",marginTop:"-10px",fontSize:"12px",marginRight:"5px"}},"".concat(null==e||null===(d=e.variant)||void 0===d||null===(a=d.unitPrice)||void 0===a?void 0:a.price," ").concat(null==e||null===(c=e.variant)||void 0===c||null===(v=c.unitPrice)||void 0===v?void 0:v.currency))));var e,i,l,r,t,o,d,a,c,v},style:{width:350},classes:{groupLabel:E.groupLabel},renderInput:function(n){return u().createElement(d.Z,(0,l.Z)({},n,{disabled:Z,label:"Select Product SKU",variant:"outlined"}))}})}}}]);