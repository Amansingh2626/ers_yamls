(self.webpackChunk_unified_order=self.webpackChunk_unified_order||[]).push([[8635],{68635:(e,l,t)=>{"use strict";t.d(l,{Z:()=>v});var n=t(74315),a=t(2411),i=t.n(a),o=t(79692),r=t(12420),c=t(56939),s=t(12231),d=t(42494),u=t(73698),p=t(63135),m=(0,o.Z)((function(e){return(0,r.Z)({container:{display:"flex",width:450,flexDirection:"column"},formControl:{margin:e.spacing(1.2),minWidth:120,width:350},selectEmpty:{marginTop:e.spacing(1.5)},labelRoot:{width:200}})}));const v=function(e){var l=e.state,t=e.dispatch,o=m(),r=(0,p.useTranslation)().t,v=(0,a.useState)(!1),y=(0,n.Z)(v,2),f=(y[0],y[1],[{label:r("delivery"),value:"delivery"},{label:r("collection"),value:"collection"}]);return i().createElement("div",{className:o.container},i().createElement(d.Z,{variant:"outlined",className:o.formControl},i().createElement(c.Z,{id:"option-label"},r("selectDeliveryOption")),i().createElement(u.Z,{key:"Asdsa",labelId:"option-label",id:"demo-simple-select-outlined",value:l.deliveryOptions.mode||"",onChange:function(e){var l={mode:e.target.value};t({type:"delivery_option_select",payload:l})},label:r("selectDeliveryOption"),inputProps:{name:"delivery-options",id:"option-label"}},f.map((function(e){return i().createElement(s.Z,{key:e.value,value:e.value},e.label)})))))}}}]);