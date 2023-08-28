(self.webpackChunk_unified_workflows=self.webpackChunk_unified_workflows||[]).push([[2204],{4579:(e,t,n)=>{"use strict";n.d(t,{Z:()=>l});var r=n(2411),a=n.n(r),o=n(8670);function i(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const l=function(e){var t,n,r=(t=a().useState(null),n=2,function(e){if(Array.isArray(e))return e}(t)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(t,n)||function(e,t){if(e){if("string"==typeof e)return i(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?i(e,t):void 0}}(t,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()),l=r[0],c=r[1],s=(0,o.useSelector)((function(e){var t;return null==e||null===(t=e.auth)||void 0===t?void 0:t.rbac}),o.shallowEqual);return a().useEffect((function(){var t,n=(null===(t=s.features.find((function(t){return t.module===e})))||void 0===t?void 0:t.features)||[];c(n)}),[s]),l}},9504:(e,t,n)=>{"use strict";n.d(t,{Z:()=>Ye});var r=n(2411),a=n.n(r),o=n(8049);const i="VIEW_ALL_WORKFLOWS",l="CREATE_WORKFLOW",c="EDIT_WORKFLOW",s="DELETE_WORKFLOW",u="VIEW_WORKFLOW_DETAILS";var m=n(4104),f=n.n(m),d=n(7603),p=n(9609),g=n(8896);const h=function(e){var t=e.disable,n=void 0!==t&&t,a=e.label,i=e.path,c=(0,p.useTranslation)().t,s=f()().checkPermission;return a=a||c("createNew"),i=i||"/home/workflows/workflow-creation/create",r.createElement(d.SeamlessButton,{component:o.Link,to:i,variant:"contained",color:"primary",disabled:n||!s(l),endIcon:r.createElement(g.Z,null)},a)};var y=n(3248);const v=function(){var e=(0,y.useSnackbar)().enqueueSnackbar,t=(0,p.useTranslation)().t;function n(e){var n=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(n=e[t])})),n}return{showSuccessMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"warning"})}}};var w=n(1744),E=n.n(w),b=n(1416);const S=n.n(b)();var k=n(4126),C="/groupmanagementsystem/v1",Z=function(e){return S("".concat(C,"/workflow/").concat(e,"?showRefIds=true"))};function A(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return O(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?O(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function O(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var I="";const x=function(e){var t=e.children,n=e.data,a=v().showErrorMessage,o=A(r.useState([]),2),i=o[0],l=o[1],c=A(r.useState(!1),2),s=c[0],u=c[1],m=A(r.useState(0),2),f=m[0],d=m[1],p=A(r.useState(20),2),g=p[0],h=p[1],y=A(r.useState(0),2),E=y[0],b=y[1],Z=A(r.useState([]),2),O=Z[0],x=Z[1],T=A(r.useState(""),2),N=(T[0],T[1]),M=A(r.useState("desc"),2),P=M[0],j=M[1],W=A(r.useState("name"),2),B=W[0],D=W[1],F=((0,w.useServerTable)({page:0,perPage:20,order:"asc",orderBy:"",filter:{}}),r.useCallback((function(e){var t=e.sortModel[0].field,n=e.sortModel[0].sort;D(t),j(n)}),[])),R=r.useCallback((function(e){var t=e.filterModel.items[0].value?e.filterModel.items[0].value:"",n=(e.filterModel.items[0].columnField,e.filterModel.items[0].columnField),r=e.filterModel.items[0].operatorValue,a=[];a.push({key:n,operator:r,value:t}),x(a),b(0)}),[]),L=r.useCallback((function(e){b(e)}),[]),z=r.useCallback((function(e){h(e)}),[]),V=(r.useCallback((function(e){N(e)}),[]),function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return Object.keys(e).reduce((function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",n=arguments.length>1?arguments[1]:void 0;return e[n]?"".concat(t?"".concat(t,","):"").concat(n,":").concat(e[n]):t.trim()}),"")}(O)),U={sort:"".concat(B,"_").concat(null==P?void 0:P.toLowerCase()),page:V===I?E:0,perPage:g,filter:JSON.stringify(O)};return r.useEffect((function(){u(!0),function(e){return S("".concat(C,"/workflow?").concat(k.stringify(e)))}(U).then((function(e){var t;0===e.resultCode?(d(null==e||null===(t=e.metadata)||void 0===t?void 0:t.totalCount),l(e.workflows),I=V):a(n)})).catch((function(e){a(e)})).finally((function(){return u(!1)}))}),[n,B,P,E,g,O]),r.createElement(r.Fragment,null,t({listData:i,loading:s,count:f,onFilterChange:R,onSort:F,setParentPage:L,setParentPerPage:z}))};var T=n(7089),N=n(8592),M=n(7277),P=n(436),j=n(4808),W=n(552),B=n(4874),D=n(9058),F=n(6054);function R(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return L(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?L(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function L(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var z=(0,j.makeStyles)((function(e){return(0,j.createStyles)({dialogWrapper:{textAlign:"center","& .MuiDialog-container":{width:"100%"},"& .MuiDialogContent-root":{marginTop:e.spacing(2),marginBottom:e.spacing(3),marginRight:"auto",marginLeft:"auto"}},fontHeavy:{fontWeight:e.typography.fontWeightBold},modalContentSpacing:{marginBottom:e.spacing(3)},btnSpace:{marginRight:e.spacing(2)},element:{width:300},button:{width:80,marginRight:e.spacing(1)},spacingLeft:{marginLeft:e.spacing(1)},horizontalOverflow:{overflowX:"auto",overflowY:"hidden"}})}));const V=function(e){var t=e.loading,n=e.listData,a=void 0===n?[]:n,i=e.count,l=e.onFilterChange,m=e.onSort,g=e.setParentPage,h=e.setParentPerPage,y=(0,p.useTranslation)().t,w=(0,o.useHistory)(),b=v(),k=b.showSuccessMessage,Z=b.showErrorMessage,A=f()().checkPermission,O=z(),I=R(r.useState(0),2),x=I[0],j=I[1],L=R(r.useState(""),2),V=L[0],U=L[1],_=R(r.useState(!1),2),H=_[0],q=_[1],$=function(){q(!1)},G=[{field:"name",headerName:y("name"),width:200},{field:"groups",headerName:y("groups"),sortable:!1,flex:1.5,renderCell:function(e){var t=e.getValue("groups").sort((function(e,t){return e.workflowOrder-t.workflowOrder}))||[];return r.createElement("div",{className:O.horizontalOverflow},t.length?t.map((function(e,t){return r.createElement(T.Z,{className:O.spacingLeft,key:"group-".concat(e.group.name,"-").concat(t),label:e.group.name,variant:"outlined",deleteIcon:r.createElement(r.Fragment,null)})})):"-")}},{field:"campaign",headerName:y("campaign"),sortable:!1,filterable:!1,width:200,renderCell:function(e){var t=e.getValue("isUpdatable");return r.createElement(r.Fragment,null,t?r.createElement("p",{className:O.fontHeavy},"All Campaigns are closed."):r.createElement("p",{className:O.fontHeavy},"A Campaign is running."))}},{field:"actions",headerName:y("actions"),sortable:!1,filterable:!1,renderCell:function(e){var t=e.getValue("isUpdatable"),n=e.getValue("isDeletable");return r.createElement("div",null,r.createElement(N.Z,{size:"small",color:"primary","aria-label":"group action buttons"},A(u)&&r.createElement(M.Z,{component:o.Link,to:"/home/workflows/workflow-creation/view/"+e.getValue("id")},r.createElement(W.Z,{fontSize:"small"})),A(c)&&r.createElement(M.Z,{component:o.Link,to:"/home/workflows/workflow-creation/edit/"+e.getValue("id"),disabled:!t},r.createElement(B.Z,{fontSize:"small"})),A(s)&&r.createElement(M.Z,{onClick:function(){q(!0),j(e.getValue("id")),U(e.getValue("name"))},disabled:!n},r.createElement(D.Z,{fontSize:"small",style:n?{color:F.Z[400]}:{}}))))},flex:1,width:250}];return r.createElement(r.Fragment,null,r.createElement(E(),{pagination:!0,paginationMode:"server",filterMode:"server",sortingMode:"server",rows:a,loading:t,columns:G,getRowId:function(e){return e.id},columnBuffer:3,rowCount:i,onSortModelChange:m,onFilterModelChange:l,onPageChange:function(e){g(e.page)},onPageSizeChange:function(e){g(0),h(e.pageSize)},autoHeight:!0}),r.createElement(d.SeamlessDialog,{open:H,onClose:$,className:O.dialogWrapper,maxWidth:"lg"},r.createElement(r.Fragment,null,r.createElement("div",{className:O.modalContentSpacing},r.createElement(P.Z,{gutterBottom:!0,id:"transition-modal-title",variant:"h6",className:O.fontHeavy},y("deleteWorkflowConfirmationTitle")),r.createElement(P.Z,{id:"transition-modal-description",variant:"body1"},y("deleteWorkflowConfirmationText",{name:V}))),r.createElement("div",{className:O.modalContentSpacing},r.createElement(d.SeamlessButton,{type:"button",variant:"outlined",color:"primary",className:O.btnSpace,onClick:$},y("no")),r.createElement(d.SeamlessButton,{type:"button",variant:"contained",color:"primary",onClick:function(){var e;(e=x,S("".concat(C,"/workflow/").concat(e),{method:"DELETE"})).then((function(e){0===e.resultCode?(k(e.resultMessage||y("deleteWorkflowSuccess")),w.push("/home/workflows")):Z(e.resultMessage||y("anErrorOccurred")),q(!1)}))}},y("yes"))))))},U=function(e){return e.data,r.createElement(r.Fragment,null,r.createElement(x,null,(function(e){var t=e.listData,n=e.loading,a=e.count,o=e.onSort,i=e.setParentPage,l=e.setParentPerPage,c=e.onFilterChange;return r.createElement(V,{listData:t,loading:n,count:a,onSort:o,setParentPage:i,setParentPerPage:l,onFilterChange:c})})))};var _=n(7114),H=n(8526),q=n(1262),$=n(4614),G=n(905),K=n(6619),X=n(7367),Y=n(6277),J=n(9692),Q=n(2420),ee=n(4339),te=n(2552),ne=n(581),re=n(5256),ae=n(1776),oe=n(8402),ie=n(6136),le=n(1324),ce=n(9378),se=n(1853);function ue(e){return function(e){if(Array.isArray(e))return de(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||fe(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function me(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||fe(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function fe(e,t){if(e){if("string"==typeof e)return de(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?de(e,t):void 0}}function de(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var pe=(0,J.Z)((function(e){return(0,Q.Z)({cardHeader:{padding:e.spacing(.2,.5),backgroundColor:e.palette.background.default,"& .MuiCardHeader-avatar":{marginRight:"0!important"}},list:{width:280,height:250,backgroundColor:e.palette.background.default,overflow:"auto",overflowX:"hidden"},button:{margin:e.spacing(.5,0),minWidth:30},checkBoxSelection:{padding:"0!important"},checkboxContainer:{"& .MuiListItemIcon-root":{minWidth:"40px!important"}},rowSpacing:{marginBottom:e.spacing(.5)}})}));function ge(e,t){return e.filter((function(e){return-1===t.indexOf(e)}))}function he(e,t){return e.filter((function(e){return-1!==t.indexOf(e)}))}function ye(e,t){var n=e.leftTitle,r=e.rightTitle,o=e.placeholder,i=e.left,l=e.right,c=e.setLeft,s=e.setRight,u=pe(),m=me(a().useState([]),2),f=m[0],p=m[1],g=he(f,i),h=he(f,l),y=function(e){return function(){var t=f.indexOf(e),n=ue(f);-1===t?n.push(e):n.splice(t,1),p(n)}};a().useEffect((function(){s(ge(l,i))}),[i]);var v=function(e,t,n){var r=me(a().useState([]),2),i=r[0],l=r[1];return a().useEffect((function(){l(ue(t))}),[t]),a().createElement("div",null,a().createElement("div",{style:{marginBottom:"5px"}},e,":"),a().createElement(ee.Z,null,a().createElement(te.Z,{className:u.cardHeader,avatar:a().createElement("div",{style:{width:270}},a().createElement(d.SeamlessInput,{fullWidth:!0,style:{fontSize:14},onKeyUp:function(e){return function(e){var n,r=null==i?void 0:i.filter((function(t){var n;return t.name.toLowerCase().match(null==e||null===(n=e.target)||void 0===n?void 0:n.value.toLowerCase())}));l(ue(r)),null!=e&&null!==(n=e.target)&&void 0!==n&&n.value||l(ue(t))}(e)},placeholder:o,startAdornment:a().createElement(ne.Z,{position:"start"},a().createElement(se.Z,{style:{fontSize:15}}))}))}),a().createElement(re.Z,null),a().createElement(ae.Z,{className:u.list,dense:!0,component:"div",role:"list"},i.map((function(e,t){return a().createElement(oe.Z,{button:!0,key:t,role:"listitem",onClick:y(e),className:u.checkboxContainer},a().createElement(ie.Z,null,a().createElement(le.Z,{tabIndex:-1,disableRipple:!0,className:u.checkBoxSelection,checked:-1!==f.indexOf(e),inputProps:{"aria-labelledby":e.name}})),a().createElement(ce.Z,{id:e.id,primary:n?"".concat(e.name,"--").concat(t+1):e.name}))})),a().createElement(oe.Z,null))))};return a().createElement(G.Z,{container:!0,spacing:2,alignItems:"center",ref:t},a().createElement(G.Z,{item:!0},v(n,i)),a().createElement(G.Z,{item:!0},a().createElement(G.Z,{container:!0,direction:"column",alignItems:"center"},a().createElement(M.Z,{variant:"outlined",size:"small",className:u.button,onClick:function(){s(l.concat(g)),c(ge(i,g)),p(ge(f,g))},disabled:0===g.length,"aria-label":"move selected right"},">"),a().createElement(M.Z,{variant:"outlined",size:"small",className:u.button,onClick:function(){c(i.concat(h)),s(ge(l,h)),p(ge(f,h))},disabled:0===h.length,"aria-label":"move selected left"},"<"))),a().createElement(G.Z,{item:!0},v(r,l,!0)))}const ve=a().forwardRef(ye);function we(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return Ee(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?Ee(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Ee(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const be=function(e){var t=e.screen,n=v().showErrorMessage,a=we(r.useState([]),2),o=a[0],i=a[1],l=we(r.useState(!1),2),c=l[0],s=l[1],u=r.useCallback((function(){s(!0),S("".concat(C,"/group/workflow?status=active")).then((function(e){0===e.resultCode?i(e.groups):i([])})).catch((function(e){n(e)})).finally((function(){return s(!1)}))}),[]);return r.useEffect((function(){"view"!==t&&u()}),[u]),{groups:o,loading:c}};var Se=n(22);function ke(){return(ke=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function Ce(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return Ze(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?Ze(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Ze(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var Ae=(0,J.Z)((function(e){return(0,Q.Z)({elements:{marginRight:e.spacing(1)},spacingRight:{marginRight:e.spacing(2)},spacingLeft:{marginLeft:e.spacing(2)},backdrop:{zIndex:e.zIndex.drawer+1,color:"#fff"},w100:{width:"100%"},rowSpacing:{marginTop:e.spacing(2)},buttonGrid:{marginTop:e.spacing(4)},successWrapper:{textAlign:"center","& .MuiSvgIcon-root":{fill:"#74A743",fontSize:"xxx-large"},"& h2":{fontSize:"22px",color:e.palette.primary.main}}})}));const Oe=function(){var e=(0,p.useTranslation)().t,t=(0,o.useHistory)(),n=Ae(),a=v().showErrorMessage,i=be({screen:"create"}),l=i.groups,c=i.loading,s=Ce(r.useState(l),2),u=s[0],m=s[1],f=Ce(r.useState([]),2),g=f[0],h=f[1],y=Ce(r.useState(!1),2),w=y[0],E=y[1],b=Ce(r.useState(!1),2),k=b[0],Z=b[1],A=H.Ry().shape({name:H.Z_().required(e("workflowNameError")),groups:H.IX().min(1,e("workflowGroupsMinError")),description:H.Z_().required(e("workflowDescriptionError"))});r.useEffect((function(){m(l)}),[l.length]);var O=function(){F("name",""),F("description",""),F("groups",[]),m(l),h([]),Z(!1)},I=(0,_.TA)({initialValues:{name:"",description:"",groups:[]},onSubmit:function(t){var n;(n={workflow:t},S("".concat(C,"/workflow"),{method:"POST",body:n})).then((function(t){0===t.resultCode?E(!0):a(t.resultMessage||e("anErrorOccurred"))}))},validationSchema:A,onReset:O}),x=I.values,N=I.errors,M=I.setFieldTouched,P=I.setFieldError,j=I.handleChange,W=I.handleSubmit,B=I.handleBlur,D=I.validateForm,F=I.setFieldValue;return r.useEffect((function(){g.length>0&&(M("groups",!0),P("groups","")),x.groups=g}),[g.length]),c||!x?r.createElement(q.Z,{className:n.backdrop,open:c||!x},r.createElement($.Z,{color:"inherit"})):r.createElement(r.Fragment,null,r.createElement(G.Z,{container:!0,className:n.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:3},r.createElement(d.SeamlessInput,{id:"name",value:x.name,required:!0,error:!!N.name,helperText:N.name?N.name:"",className:(0,Y.default)(n.elements,n.w100),name:"name",label:e("name"),onChange:j,onBlur:B}))),r.createElement(G.Z,{container:!0,className:n.rowSpacing},r.createElement(G.Z,{item:!0,xs:12},r.createElement(ve,{left:u,right:g,setLeft:m,setRight:h,rightTitle:e("groupsAssigned"),placeholder:e("searchGroups"),leftTitle:e("allGroups")}))),r.createElement(G.Z,{container:!0,className:n.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:6},r.createElement(Se.ZP,{multiple:!0,id:"assigned-groups",options:[],defaultValue:[],freeSolo:!0,value:g,onChange:j,renderTags:function(e,t){return e.map((function(n,a){return r.createElement(r.Fragment,null,r.createElement(T.Z,ke({key:a,variant:"outlined",label:n.name,deleteIcon:r.createElement(r.Fragment,null)},t({index:a}))),a!==e.length-1&&r.createElement("span",null,"---"))}))},renderInput:function(t){return r.createElement(K.Z,ke({},t,{required:!0,error:!!N.groups,helperText:N.groups?N.groups:"",label:e("groups")}))}}))),r.createElement(G.Z,{container:!0,className:n.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:6},r.createElement(d.SeamlessInput,{id:"description",value:x.description,multiline:!0,rows:5,required:!0,error:!!N.description,helperText:N.description?N.description:"",className:(0,Y.default)(n.elements,n.w100),name:"description",label:e("description"),onChange:j,onBlur:B}))),r.createElement(G.Z,{container:!0,className:n.buttonGrid},r.createElement(G.Z,{item:!0,xs:12,sm:2,className:n.spacingRight},r.createElement(d.SeamlessButton,{onClick:function(t){D().then((function(t){var n,r=Object.values(t);r.length>0?(n=r[0],a("string"==typeof n?n:n?n[Object.keys(n)[0]]:e("anErrorOccurred"))):W()}))},id:"workflow-submit-button",variant:"contained",color:"primary",className:(0,Y.default)(n.elements,n.w100),size:"large"},e("saveButton"))),r.createElement(G.Z,{item:!0,xs:12,sm:2},r.createElement(d.SeamlessButton,{onClick:function(e){return t.goBack()},id:"workflow-cancel-button",variant:"outlined",color:"secondary",className:(0,Y.default)(n.elements,n.w100),size:"large"},e("cancel"))),r.createElement(G.Z,{item:!0,xs:12,sm:2,className:n.spacingLeft},r.createElement(d.SeamlessButton,{onClick:function(e){Z(!0)},id:"workflow-reset-button",variant:"outlined",type:"reset",className:(0,Y.default)(n.elements,n.w100),size:"large"},e("reset")))),r.createElement(_e,{onClose:function(){Z(!1)},onOk:function(){return O()},open:k},r.createElement("div",{className:n.successWrapper},r.createElement("h2",null,e("resetConfirmationTitle")),r.createElement("p",null,e("resetConfirmationMessage")))),r.createElement(_e,{open:w,onClose:function(){return E(!1)},onOk:function(){return t.goBack()}},r.createElement("div",{className:n.successWrapper},r.createElement(X.Z,null),r.createElement("h2",null,e("workflowCreateSuccess")))))};function Ie(){return(Ie=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function xe(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return Te(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?Te(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Te(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}var Ne=(0,J.Z)((function(e){return(0,Q.Z)({elements:{marginRight:e.spacing(1)},spacingRight:{marginRight:e.spacing(2)},spacingLeft:{marginLeft:e.spacing(2)},backdrop:{zIndex:e.zIndex.drawer+1,color:"#fff"},w100:{width:"100%"},rowSpacing:{marginTop:e.spacing(2)},largeMarginTop:{marginTop:e.spacing(4)},dividerSpacing:{marginTop:e.spacing(4),backgroundColor:"#b5b5b5"},successWrapper:{textAlign:"center","& .MuiSvgIcon-root":{fill:"#74A743",fontSize:"xxx-large"},"& h2":{fontSize:"22px",color:e.palette.primary.main}}})}));const Me=function(e){var t=e.onSubmit,n=e.initialValues,a=e.edit,i=e.workflowLoading,l=e.successDailog,c=e.setSuccessDailog,s=e.associatedCampaigns,u=(0,p.useTranslation)().t,m=(0,o.useHistory)(),f=Ne(),g=be({screen:a?"edit":"view"}),h=g.groups,y=g.loading,v=xe(r.useState([]),2),w=v[0],E=v[1],b=xe(r.useState([]),2),S=b[0],k=b[1],C=xe(r.useState(!1),2),Z=C[0],A=C[1],O=H.Ry().shape({name:H.Z_().required(u("workflowNameError")),description:H.Z_().required(u("workflowDescriptionError")),groups:H.IX().min(1,u("workflowGroupsMinError"))}),I=(0,_.TA)({initialValues:{name:"",description:"",groups:[]},onSubmit:t,validationSchema:O}),x=I.values,N=I.errors,M=I.touched,j=I.setFieldValue,W=I.setFieldError,B=I.setFieldTouched,D=I.handleChange,F=I.handleBlur,R=I.handleSubmit;r.useEffect((function(){if(n){k(n.groups);var e=new Set(n.groups.map((function(e){return e.id}))),t=w.filter((function(t){var n=t.id;return!e.has(n)}));E(t),j("name",n.name),j("description",n.description),j("groups",n.groups)}}),[n]),r.useEffect((function(){S.length>0?(B("groups",!0),W("groups","")):W("groups",u("workflowGroupsMinError")),x.groups=S}),[S.length]),r.useEffect((function(){E(h)}),[h.length]);var L=function(e){var t=e.paymentInformation.campaignPaymentInformation.payoutWorkflowId,n=e.profileInformation.designWorkflowId;return t&&n?u("payoutAndCampaignDesign"):t&&!n?u("payoutApproval"):!t&&n?u("campaignDesignApproval"):""};return y||i||!x?r.createElement(q.Z,{className:f.backdrop,open:y||i||!x},r.createElement($.Z,{color:"inherit"})):r.createElement(r.Fragment,null,r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:3},r.createElement(d.SeamlessInput,{id:"name",value:x.name,required:!0,error:!(!M.name||!N.name),helperText:M.name&&N.name?N.name:"",onBlur:F,className:(0,Y.default)(f.elements,f.w100),name:"name",readOnly:!a,label:u("name"),onChange:D})),a&&r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:8},r.createElement(ve,{left:w,right:S,setLeft:E,setRight:k,rightTitle:u("groupsAssigned"),placeholder:u("searchGroups"),leftTitle:u("allGroups")}))),r.createElement(G.Z,{item:!0,xs:12,sm:6},r.createElement(Se.ZP,{multiple:!0,id:"assigned-groups",options:[],defaultValue:[],freeSolo:!0,disableClearable:!0,value:S,onChange:D,renderTags:function(e,t){return e.map((function(n,a){return r.createElement("div",{key:a},r.createElement(T.Z,Ie({variant:"outlined",label:n.name,deleteIcon:r.createElement(r.Fragment,null)},t({index:a}))),a!==e.length-1&&r.createElement("span",null,"---"))}))},renderInput:function(e){return r.createElement(K.Z,Ie({},e,{error:!(!M.groups||!N.groups),helperText:M.groups&&N.groups?N.groups:"",label:u("groups")}))}}))),r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:6},r.createElement(d.SeamlessInput,{id:"description",value:x.description,multiline:!0,rows:5,required:!0,readOnly:!a,error:!(!M.description||!N.description),helperText:M.description&&N.description?N.description:"",onBlur:F,className:(0,Y.default)(f.elements,f.w100),name:"description",label:u("description"),onChange:D}))),s&&s.length>0?r.createElement(r.Fragment,null,r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:12},r.createElement(P.Z,{variant:"h5",color:"primary"},u("associatedCampaigns","Associated Campaigns")))),s.map((function(e,t){return r.createElement("div",{className:f.largeMarginTop,key:"associated-campaign-".concat(t)},r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:4},r.createElement(d.SeamlessInput,{id:"campaign--name",value:e.profileInformation.name,className:(0,Y.default)(f.elements,f.w100),name:"campaign--name",readOnly:!0,label:u("campaignName")})),r.createElement(G.Z,{item:!0,xs:12,sm:4},r.createElement(d.SeamlessInput,{id:"campaign--status",value:(n=e.profileInformation.state.status,n.replace(/\w\S*/g,(function(e){return e.charAt(0).toUpperCase()+e.substr(1).toLowerCase()}))),className:(0,Y.default)(f.elements,f.w100),name:"campaign--status",readOnly:!0,label:u("status")}))),r.createElement(G.Z,{container:!0,spacing:3,className:f.rowSpacing},r.createElement(G.Z,{item:!0,xs:12,sm:4},r.createElement(d.SeamlessInput,{id:"campaign--associatedFor",value:L(e),className:(0,Y.default)(f.elements,f.w100),name:"campaign--associatedFor",readOnly:!0,label:u("associatedFor")}))),t!==s.length-1&&r.createElement(re.Z,{className:(0,Y.default)(f.dividerSpacing)}));var n}))):r.createElement(G.Z,{container:!0,spacing:3,className:(0,Y.default)(f.rowSpacing,f.largeMarginTop)},r.createElement(G.Z,{item:!0,xs:12},r.createElement(P.Z,{variant:"h5",color:"textPrimary"},u("noAssociatedCampaignsFound")))),r.createElement(G.Z,{container:!0,spacing:3,className:f.largeMarginTop},a&&r.createElement(G.Z,{item:!0,xs:12,sm:2,className:f.spacingRight},r.createElement(d.SeamlessButton,{onClick:function(e){return R()},id:"workflow-submit-button",variant:"contained",color:"primary",className:(0,Y.default)(f.elements,f.w100),size:"large"},u("saveButton"))),r.createElement(G.Z,{item:!0,xs:12,sm:2},r.createElement(d.SeamlessButton,{onClick:function(e){return m.goBack()},id:"workflow-cancel-button",variant:"outlined",color:"secondary",className:(0,Y.default)(f.elements,f.w100),size:"large"},u("cancel"))),a&&r.createElement(G.Z,{item:!0,xs:12,sm:2},r.createElement(d.SeamlessButton,{onClick:function(e){A(!0)},id:"workflow-reset-button",variant:"outlined",type:"reset",className:(0,Y.default)(f.elements,f.w100),size:"large"},u("reset")))),a&&r.createElement(_e,{onClose:function(){A(!1)},onOk:function(){return function(){j("name",n.name),j("description",n.description),j("groups",n.groups),k(n.groups);var e=new Set(n.groups.map((function(e){return e.id}))),t=w.filter((function(t){var n=t.id;return!e.has(n)}));E(t),A(!1)}()},open:Z},r.createElement("div",{className:f.successWrapper},r.createElement("h2",null,u("resetConfirmationTitle")),r.createElement("p",null,u("resetConfirmationMessage")))),a&&r.createElement(_e,{open:l,onClose:function(){return c(!1)},onOk:function(){return m.goBack()}},r.createElement("div",{className:f.successWrapper},r.createElement(X.Z,null),r.createElement("h2",null,u("workflowUpdateSuccess")))))};function Pe(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return je(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?je(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function je(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const We=function(e){var t=e.id,n=v().showErrorMessage,a=Pe(r.useState(),2),o=a[0],i=a[1],l=Pe(r.useState(),2),c=l[0],s=l[1],u=Pe(r.useState(!1),2),m=u[0],f=u[1],d=r.useCallback((function(){f(!0),Z(t).then((function(e){if(0===e.resultCode){var t=e.workflow,n=t.groups.sort((function(e,t){return e.workflowOrder-t.workflowOrder})).map((function(e){return e.group}));if(t.groups=n,i(t),0!==e.referenceIds.length){var r=e.referenceIds.join(",");p(r)}}})).catch((function(e){n(e)})).finally((function(){return f(!1)}))}),[]),p=function(e){var t;(t=e,S("/scc/v1/campaign/trackerUIDs?trackerUIDs=".concat(t))).then((function(e){0===e.resultCode&&s(e.campaigns)})).catch((function(e){n(e)}))};return r.useEffect((function(){d()}),[d]),r.createElement(Me,{onSubmit:function(e){return null},initialValues:o,associatedCampaigns:c,edit:!1,workflowLoading:m,successDailog:!1,setSuccessDailog:function(){return!1}})};function Be(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function De(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?Be(Object(n),!0).forEach((function(t){Fe(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):Be(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function Fe(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function Re(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return Le(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?Le(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Le(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const ze=function(e){var t=e.id,n=(0,p.useTranslation)().t,a=v().showErrorMessage,o=Re(r.useState(!1),2),i=o[0],l=o[1],c=Re(r.useState(),2),s=c[0],u=c[1],m=Re(r.useState(!1),2),f=m[0],d=m[1],g=r.useCallback((function(){d(!0),Z(t).then((function(e){if(0===e.resultCode){var t=e.workflow,n=t.groups.sort((function(e,t){return e.workflowOrder-t.workflowOrder})).map((function(e){return e.group}));t.groups=n,u(t)}else u(void 0)})).catch((function(e){a(e)})).finally((function(){return d(!1)}))}),[]);return r.useEffect((function(){g()}),[g]),r.createElement(Me,{onSubmit:function(e){var t;(t={workflow:De(De({},s),e)},S("".concat(C,"/updateWorkflow"),{method:"PUT",body:t})).then((function(e){0===e.resultCode?l(!0):a(e.resultMessage||n("anErrorOccurred"))}))},initialValues:s,edit:!0,workflowLoading:f,successDailog:i,setSuccessDailog:l})};var Ve=n(8724),Ue=(0,J.Z)((function(e){return(0,Q.Z)({root:{marginTop:e.spacing(5),width:"100%",padding:e.spacing(2)},dialogWrapper:{textAlign:"center","& .MuiDialog-container":{width:"100%"},"& .MuiDialogContent-root":{marginTop:e.spacing(2),marginBottom:e.spacing(3),marginRight:"auto",marginLeft:"auto"}},fontHeavy:{fontWeight:e.typography.fontWeightBold},modalContentSpacing:{marginTop:e.spacing(3),marginBottom:e.spacing(3)},btnSpace:{marginRight:e.spacing(2)},content:{textAlign:"left",maxHeight:400,minWidth:550,overflowY:"hidden"}})}));const _e=function(e){var t=e.open,n=e.onClose,a=e.onOk,o=e.children,i=e.maxWidth,l=void 0===i?"md":i,c=e.contentStyle,s=void 0===c?"":c,u=e.disableBackdropClick,m=void 0!==u&&u,f=e.closeButtonText,g=void 0===f?null:f,h=e.okButtonText,y=void 0===h?null:h,v=Ue(),w=(0,p.useTranslation)().t;return r.createElement(d.SeamlessDialog,{open:t,onClose:n,className:v.dialogWrapper,maxWidth:l,fullWidth:!0,disableBackdropClick:m},r.createElement("div",null,r.createElement(r.Fragment,null,r.createElement(Ve.Z,{className:(0,Y.default)(v.content,s)},o),r.createElement(d.SeamlessButton,{type:"button",variant:"outlined",color:"primary",className:v.btnSpace,onClick:n},g||w("closeButton")),a&&r.createElement(d.SeamlessButton,{type:"button",variant:"contained",color:"primary",onClick:a},y||w("okay")))))};var He=n(3553);const qe=function(){var e=(0,p.useTranslation)().t;return r.createElement(d.SeamlessContainer,{title:e("viewWorkflowsPageTitle"),description:e("viewWorkflowsPageDescription")},r.createElement(He.Z,{display:"flex",flexDirection:"row-reverse",alignItems:"center"},r.createElement(h,null)),r.createElement(U,null))};function $e(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const Ge=function(){var e,t,n=(0,p.useTranslation)().t,a=(e=r.useState(!1),t=2,function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,a,o=[],i=!0,l=!1;try{for(n=n.call(e);!(i=(r=n.next()).done)&&(o.push(r.value),!t||o.length!==t);i=!0);}catch(e){l=!0,a=e}finally{try{i||null==n.return||n.return()}finally{if(l)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return $e(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?$e(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()),i=(a[0],a[1],v());return i.showErrorMessage,i.showSuccessMessage,(0,o.useHistory)(),r.createElement(d.SeamlessContainer,{title:n("createWorkflowPageTitle"),description:n("createWorkflowPageDescription")},r.createElement(Oe,null))},Ke=function(){var e=(0,p.useTranslation)().t,t=(0,o.useParams)().id;return r.createElement(d.SeamlessContainer,{title:e("editWorkflowPageTitle"),description:e("editWorkflowPageDescription")},r.createElement(ze,{id:t}))},Xe=function(){var e=(0,p.useTranslation)().t,t=(0,o.useParams)().id;return r.createElement(d.SeamlessContainer,{title:e("viewWorkflowPageTitle"),description:e("viewWorkflowPageDescription")},r.createElement(We,{id:t}))},Ye=function(){var e=(0,o.useRouteMatch)().path,t=f()().AccessControlled;return r.createElement(o.Switch,null,r.createElement(o.Route,{path:e+"/edit/:id"},r.createElement(t,{feature:c},r.createElement(Ke,null))),r.createElement(o.Route,{path:e+"/view/:id"},r.createElement(t,{feature:u},r.createElement(Xe,null))),r.createElement(o.Route,{path:e+"/create"},r.createElement(t,{feature:l},r.createElement(Ge,null))),r.createElement(o.Route,null,r.createElement(t,{feature:i},r.createElement(qe,null))))}}}]);