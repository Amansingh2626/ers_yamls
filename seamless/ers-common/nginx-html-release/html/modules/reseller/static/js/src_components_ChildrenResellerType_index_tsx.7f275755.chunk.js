(self.webpackChunk_unified_reseller=self.webpackChunk_unified_reseller||[]).push([["src_components_ChildrenResellerType_index_tsx","node_modules_babel_runtime_helpers_esm_slicedToArray_js-node_modules_babel_runtime_helpers_es-080618"],{61357:(e,t,n)=>{"use strict";function r(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}n.d(t,{Z:()=>r})},74315:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(70237);function o(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,o,s=[],c=!0,a=!1;try{for(n=n.call(e);!(c=(r=n.next()).done)&&(s.push(r.value),!t||s.length!==t);c=!0);}catch(e){a=!0,o=e}finally{try{c||null==n.return||n.return()}finally{if(a)throw o}}return s}}(e,t)||(0,r.Z)(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}},11746:(e,t,n)=>{"use strict";n.d(t,{Z:()=>s});var r=n(61357),o=n(70237);function s(e){return function(e){if(Array.isArray(e))return(0,r.Z)(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||(0,o.Z)(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}},70237:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(61357);function o(e,t){if(e){if("string"==typeof e)return(0,r.Z)(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?(0,r.Z)(e,t):void 0}}},84496:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(61416);const o=n.n(r)()},58752:(e,t,n)=>{"use strict";n.d(t,{K:()=>i});var r,o,s,c=n(84496),a=window.config,l=null!==(r=null==a?void 0:a.showActiveContracts)&&void 0!==r&&r,u=null!==(o=null==a||null===(s=a.resellerAdditionalConfig)||void 0===s?void 0:s.activeContractLimit)&&void 0!==o?o:0,i=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",t=(e+=l?";contractStatus=1":"").includes("?")?"&":"?",n=u>0&&!e.includes("size=")?"".concat(t,"size=").concat(u):"";return e+=n,(0,c.Z)("/acms/v2/contracts".concat(e))}},24157:(e,t,n)=>{"use strict";n.d(t,{T:()=>o});var r=n(84496),o=function(e){return(0,r.Z)("/groupmanagementsystem/v1/group/member/findGroups?userId=".concat(e))}},96545:(e,t,n)=>{"use strict";n.d(t,{fZ:()=>r.fZ,sC:()=>r.sC,CQ:()=>r.CQ,hm:()=>r.hm,c1:()=>r.c1,pN:()=>r.pN,wv:()=>r.wv,OT:()=>r.OT,it:()=>r.it,eW:()=>r.eW,Fg:()=>r.Fg,Nk:()=>r.Nk,qu:()=>r.qu,Nb:()=>r.Nb,kS:()=>r.kS,GX:()=>r.GX,iA:()=>r.iA,OV:()=>r.OV,gH:()=>r.gH,E5:()=>r.E5,wm:()=>r.wm,TF:()=>r.TF,Ah:()=>r.Ah,HB:()=>r.HB,XD:()=>r.XD,FF:()=>r.FF,lX:()=>r.lX,Gh:()=>r.Gh,NF:()=>r.NF,cG:()=>o.cG,ZV:()=>o.ZV,cY:()=>o.cY,F3:()=>o.F3,b7:()=>o.b7,QX:()=>s.QX,lg:()=>s.lg,uP:()=>s.uP,G0:()=>s.G0,Iq:()=>s.Iq,ec:()=>c.ec,p9:()=>c.p9,ob:()=>c.ob,Kf:()=>a.K,t9:()=>l.t9,Sb:()=>l.Sb,wy:()=>l.wy,sQ:()=>l.ZV,g4:()=>l.g4,j8:()=>l.j8,WY:()=>l.WY,TJ:()=>u.T});var r=n(14674),o=n(31718),s=n(1111),c=n(95191),a=n(58752),l=n(35005),u=n(24157)},95191:(e,t,n)=>{"use strict";n.d(t,{ec:()=>o,ob:()=>s,Ww:()=>c,l4:()=>a,p9:()=>l});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/password/policies",{method:"POST",body:e})},s=function(){return(0,r.Z)("/dms/v1/password/policies")},c=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e))},a=function(e,t){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"PUT",body:t})},l=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"DELETE"})}},1111:(e,t,n)=>{"use strict";n.d(t,{fQ:()=>o,lg:()=>s,Iq:()=>c,uP:()=>a,G0:()=>l,Np:()=>u,QX:()=>i});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/resellers/types/create",{method:"POST",body:e})},s=function(){return(0,r.Z)("/dms/v1/resellers/types/all")},c=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},a=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/children"))},l=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e))},u=function(e){return(0,r.Z)("/dms/v1/resellers/types/update",{method:"PUT",body:e})},i=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e),{method:"DELETE"})}},14674:(e,t,n)=>{"use strict";n.d(t,{Nk:()=>u,Fg:()=>i,OV:()=>d,FF:()=>m,iA:()=>f,eW:()=>h,gH:()=>p,lX:()=>v,pN:()=>y,wv:()=>g,hm:()=>Z,kS:()=>b,uZ:()=>w,c0:()=>T,c1:()=>S,CQ:()=>P,E5:()=>E,NF:()=>O,qu:()=>I,fB:()=>k,OT:()=>_,HB:()=>D,XD:()=>R,wm:()=>N,it:()=>G,Gh:()=>q,sC:()=>U,fZ:()=>X,Ah:()=>B,v$:()=>W,GX:()=>j,Nb:()=>L,TF:()=>M});var r,o,s=n(84496),c=window.config||{},a=(null==c?void 0:c.defaultPerPageItems)||20,l=null!==(r=null==c||null===(o=c.resellerAdditionalConfig)||void 0===o?void 0:o.callIsBasicResellerInfoAPI)&&void 0!==r&&r,u=function(){return(0,s.Z)("/groupmanagementsystem/v1/group?status=active")},i=function(e){return(0,s.Z)("/groupmanagementsystem/v1/group/name/".concat(e))},d=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0,n=arguments.length>2?arguments[2]:void 0,r=n||a,o=t||n?"?limit=".concat(r,"&offset=").concat(t):"";return(0,s.Z)("/dms/v1/resellers/resellerChildren/".concat(e).concat(o))},m=function(e,t,n){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"",o=l?"searchResellerBasicInfoByAttribute":"searchResellersByAttribute";return r&&(o=r),(0,s.Z)("/dms/auth/".concat(o,"?limit=").concat(t,"&offSet=").concat(n),{method:"POST",body:e})},f=function(e){return(0,s.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},h=function(e,t,n){var r=n?"allowedTypes":t?"descendants":"children";return(0,s.Z)("/dms/v1/resellers/types/".concat(e,"/").concat(r))},p=function(e){return(0,s.Z)("/dms/auth/getResellerInfo",{method:"POST",body:e})},v=function(e){return(0,s.Z)("/dms/auth/updateReseller",{method:"POST",body:e})},y=function(e){return(0,s.Z)("/dms/auth/changeResellerType",{method:"POST",body:e})},g=function(e){return(0,s.Z)("/dms/auth/v1/resellerChangeState",{method:"POST",body:e})},Z=function(e,t,n){return(0,s.Z)("/dms/v1/lifecycle/dealer/status/".concat(n,"/").concat(t),{method:"POST",body:e})},b=function(){return(0,s.Z)("/lifecycle/reseller/status/fetch/labels")},w=function(e){return(0,s.Z)("/dms/auth/expirePassword",{method:"POST",body:e})},T=function(e){return(0,s.Z)("/dms/auth/forgetPassword",{method:"POST",body:e})},S=function(e){return(0,s.Z)("/dms/auth/changeParent?newResellerParentId=".concat(e.newResellerParentId,"&resellerId=").concat(e.resellerId),{method:"POST",body:e})},P=function(e){return(0,s.Z)("/dms/auth/generateId",{method:"POST",body:e})},E=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"DMS";return(0,s.Z)("/template/v1/component/".concat(t,"/type/").concat(e))},O=function(e){return(0,s.Z)("/iprs/iprs-complete-check",{method:"POST",body:e})},I=function(e){return(0,s.Z)(e)},A=0,C=null,k=function(e){var t,n,r=new Headers;r.append("authorization",localStorage.getItem("token")||""),r.append("Accept-Language","en-US;en-IN;q=0.9,en-US;q=0.7,en-GB;q=0.6");var o={method:"POST",headers:r,body:e,redirect:"follow"},s="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat("/api/dms/auth/addReseller"),c=null===(t=window)||void 0===t||null===(n=t.config)||void 0===n?void 0:n.isAllowedTokenRefresh;return fetch(s,o).then((function(t){var n=t.headers.get("authorization");return n?localStorage.setItem("token",n):c&&A<=0&&localStorage.getItem("token")&&(C=e,F().then((function(e){A=0,C&&k(C).then((function(e){0===e.resultCode&&window.history.back()}))})).catch((function(e){++A}))),t.json()}))},F=function(){var e="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat(window.config.service,"/refreshToken");return(0,s.Z)(e,{method:"POST"})},_=function(e){return(0,s.Z)("/dms/v1/customers/?nationalId=".concat(e))},D=function(e){return(0,s.Z)("/lifecycle/reseller/".concat(e,"/transactions"))},R=function(e){return(0,s.Z)("/dms/v1/resellers/updatePayLimit",{method:"PUT",body:e})},N=function(e){return(0,s.Z)("/dms/auth/userAlreadyExists",{method:"POST",body:e})},G=function(e){return(0,s.Z)("/dms/auth/deleteResellerUsers",{method:"DELETE",body:e})},q=function(e){return(0,s.Z)("/dms/auth/updateResellerUsers",{method:"PUT",body:e})},U=function(e){return(0,s.Z)("/dms/auth/addResellerUsers",{method:"POST",body:e})},X=function(e,t){return(0,s.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member"),{method:"POST",body:t})},B=function(e,t){return(0,s.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member?memberIds=").concat(t),{method:"DELETE"})},W=function(e,t,n){return(0,s.Z)("/dms/auth/bulkSearchResellersByAttribute?limit=".concat(t,"&offSet=").concat(n),{method:"POST",body:e})},j=function(){return(0,s.Z)("/nms/api/messageTemplateType/CUSTOM/messages?page=0&size=".concat(a,"&sort=id%3Aasc"))},L=function(){return(0,s.Z)("/scc/v1/frequencies")},M=function(e,t){return(0,s.Z)("/txe/reseller/".concat(e,"SubReseller"),{method:"POST",body:t})}},31718:(e,t,n)=>{"use strict";n.d(t,{cG:()=>o,F3:()=>s,cY:()=>c,b7:()=>a,ZV:()=>l});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/roles/5",{method:"POST",body:e})},s=function(){return(0,r.Z)("/dms/v1/roles/")},c=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e))},a=function(e,t){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"PUT",body:t})},l=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"DELETE"})}},35005:(e,t,n)=>{"use strict";n.d(t,{j8:()=>o,g4:()=>s,wy:()=>c,t9:()=>a,WY:()=>l,ZV:()=>u,Sb:()=>i});var r=n(84496),o=function(){return(0,r.Z)("/dms/v1/template/dropdown")},s=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e))},c=function(){return(0,r.Z)("/dms/v1/template/dropdown/fields/all")},a=function(e){return(0,r.Z)("/dms/v1/template/dropdown",{method:"POST",body:e})},l=function(e,t){return(0,r.Z)("/dms/v1/template/dropdown/".concat(t),{method:"PUT",body:e})},u=function(e){return(0,r.Z)("/dms/v1/template/dropdown/byId/".concat(e),{method:"GET"})},i=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e),{method:"DELETE"})}},51057:(e,t,n)=>{"use strict";n.d(t,{Z:()=>l});var r=n(74315),o=n(2411),s=n(96545),c=n(23664),a=n(29609);const l=function(e){var t=e.children,n=e.isDescendants,l=void 0!==n&&n,u=e.resellerTypeId,i=e.onlyAllowedTypes,d=o.useState(!1),m=(0,r.Z)(d,2),f=m[0],h=m[1],p=(0,c.Z)().showErrorMessage,v=(0,a.useTranslation)().t,y=o.useState([]),g=(0,r.Z)(y,2),Z=g[0],b=g[1],w=o.useCallback((function(){u&&(h(!0),(0,s.eW)(u,l,i).then((function(e){0===e.resultCode?b(e.types):p(e.resultDescription||v("noDataFoundError","Something wrong, No data found."))})).catch((function(e){return p(e)})).finally((function(){return h(!1)})))}),[u]);return o.useEffect((function(){w()}),[u]),o.createElement(o.Fragment,null,t({resellerTypes:Z,loading:f}))}},12541:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>m});var r=n(11746),o=n(2411),s=n(70773),c=n(29609),a=n(98069),l=n(51057),u=n(6277),i=n(38670),d=null;const m=function(e){var t,n,m,f,h,p,v=e.name,y=e.value,g=e.handleChange,Z=e.resellerType,b=e.onlyAllowedTypes,w=e.label,T=e.classes,S=e.helperText,P=e.required,E=void 0!==P&&P,O=e.disabled,I=void 0!==O&&O,A=e.editView,C=void 0!==A&&A,k=e.isDescendants,F=void 0!==k&&k,_=e.errors,D=void 0===_?{}:_,R=(0,c.useTranslation)().t,N=(0,i.useSelector)((function(e){return e.auth})).user;if(N&&null!==(t=N.info)&&void 0!==t&&t.resellerData&&(d=null===(h=N.info)||void 0===h||null===(p=h.resellerData)||void 0===p?void 0:p.resellerTypeId),Z=null!==(n=Z)&&void 0!==n?n:d,w=null!==(m=w)&&void 0!==m?m:R("childResellerType","Child Reseller type"),S=null!==(f=S)&&void 0!==f?f:R("selectChildResellerCode","Select Child Reseller type"),!Z)return null;var G=[{id:"",name:R("pleaseSelect_","Please select",{fieldName:"reseller types"})}];return C&&y&&I&&G.push({id:y,name:y}),o.createElement(l.Z,{isDescendants:F,resellerTypeId:Z,onlyAllowedTypes:b},(function(e){var t=e.resellerTypes,n=e.loading;return o.createElement(o.Fragment,null,n?o.createElement(a.Z,null):o.createElement(s.SeamlessDropDown,{id:"children-reseller-types-drop-down",required:E,disabled:I,name:v,fullWidth:!0,error:!!D[v],value:y,label:w,helperText:S,onChange:g,className:T&&(0,u.default)(T.element),options:[].concat(G,(0,r.Z)(t)).map((function(e){return{value:e.id,key:"".concat(e.name)}}))}))}))}},23664:(e,t,n)=>{"use strict";n.d(t,{Z:()=>s});var r=n(19721),o=n(29609);const s=function(){var e=(0,r.useSnackbar)().enqueueSnackbar,t=(0,o.useTranslation)().t;function n(e){var n=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(n=e[t])})),n}return{showSuccessMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"warning"})}}}}}]);