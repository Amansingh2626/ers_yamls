(self.webpackChunk_unified_wallet=self.webpackChunk_unified_wallet||[]).push([[5543],{7442:(n,e,t)=>{"use strict";t.r(e),t.d(e,{SnackbarContent:()=>W,SnackbarProvider:()=>an,useSnackbar:()=>sn,withSnackbar:()=>cn});var r=t(9037),o=t.n(r),a=t(8576),i=t(6277),c=t(2339),s=t(2420),u=t(5459),l=t(1128),d=t(9692),f=t(2113),p=t(2379),m=t(1292),g=t(3463),h=t.n(g);function k(n,e){for(var t=0;t<e.length;t++){var r=e[t];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(n,r.key,r)}}function v(){return(v=Object.assign||function(n){for(var e=1;e<arguments.length;e++){var t=arguments[e];for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&(n[r]=t[r])}return n}).apply(this,arguments)}function E(n,e){if(null==n)return{};var t,r,o={},a=Object.keys(n);for(r=0;r<a.length;r++)t=a[r],e.indexOf(t)>=0||(o[t]=n[t]);return o}var b=o().createContext(),x={root:{},anchorOriginTopCenter:{},anchorOriginBottomCenter:{},anchorOriginTopRight:{},anchorOriginBottomRight:{},anchorOriginTopLeft:{},anchorOriginBottomLeft:{}},C={containerRoot:{},containerAnchorOriginTopCenter:{},containerAnchorOriginBottomCenter:{},containerAnchorOriginTopRight:{},containerAnchorOriginBottomRight:{},containerAnchorOriginTopLeft:{},containerAnchorOriginBottomLeft:{}},y=20,O=4,w=6,S=2,D={maxSnack:3,dense:!1,hideIconVariant:!1,variant:"default",autoHideDuration:5e3,anchorOrigin:{vertical:"bottom",horizontal:"left"},TransitionComponent:c.Z,transitionDuration:{enter:225,exit:195}},L=function(n){return n.charAt(0).toUpperCase()+n.slice(1)},T=function(n){return Object.keys(n).filter((function(n){return!C[n]})).reduce((function(e,t){var r;return v({},e,((r={})[t]=n[t],r))}),{})},A={TIMEOUT:"timeout",CLICKAWAY:"clickaway",MAXSNACK:"maxsnack",INSTRUCTED:"instructed"},I=function(n){return"anchorOrigin"+n},R=function(n){var e=n.vertical,t=n.horizontal;return"anchorOrigin"+L(e)+L(t)},H=function(n){return"variant"+L(n)},M=function(n){return!!n||0===n},N=function(n){return"number"==typeof n||null===n};function q(n,e,t){return void 0===n&&(n={}),void 0===e&&(e={}),void 0===t&&(t={}),v({},t,{},e,{},n)}var V=function(n){var e;return(0,s.Z)({root:(e={display:"flex",flexWrap:"wrap",flexGrow:1},e[n.breakpoints.up("sm")]={flexGrow:"initial",minWidth:288},e)})},j=(0,r.forwardRef)((function(n,e){var t=n.classes,r=n.className,a=E(n,["classes","className"]);return o().createElement("div",Object.assign({ref:e,className:(0,i.default)(t.root,r)},a))})),W=(0,u.Z)(V)(j),Z={right:"left",left:"right",bottom:"up",top:"down"},P=function(n){return"center"!==n.horizontal?Z[n.horizontal]:Z[n.vertical]},z=function(n,e){return{container:n.collapseContainer,wrapper:(0,i.default)(n.collapseWrapper,e&&n.collapseWrapperDense),wrapperInner:n.collapseWrapperInner}},B=function(n){return o().createElement(p.Z,Object.assign({},n),o().createElement("path",{d:"M12 2C6.5 2 2 6.5 2 12S6.5 22 12 22 22 17.5 22 12 17.5 2 12 2M10 17L5 12L6.41\n        10.59L10 14.17L17.59 6.58L19 8L10 17Z"}))},_=function(n){return o().createElement(p.Z,Object.assign({},n),o().createElement("path",{d:"M13,14H11V10H13M13,18H11V16H13M1,21H23L12,2L1,21Z"}))},U=function(n){return o().createElement(p.Z,Object.assign({},n),o().createElement("path",{d:"M12,2C17.53,2 22,6.47 22,12C22,17.53 17.53,22 12,22C6.47,22 2,17.53 2,12C2,\n        6.47 6.47,2 12,2M15.59,7L12,10.59L8.41,7L7,8.41L10.59,12L7,15.59L8.41,17L12,\n        13.41L15.59,17L17,15.59L13.41,12L17,8.41L15.59,7Z"}))},K=function(n){return o().createElement(p.Z,Object.assign({},n),o().createElement("path",{d:"M13,9H11V7H13M13,17H11V11H13M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,\n        0 22,12A10,10 0 0,0 12,2Z"}))},X={fontSize:20,marginInlineEnd:8},Q={default:void 0,success:o().createElement(B,{style:X}),warning:o().createElement(_,{style:X}),error:o().createElement(U,{style:X}),info:o().createElement(K,{style:X})};function Y(n,e){return n.reduce((function(n,t){return null==t?n:function(){for(var r=arguments.length,o=new Array(r),a=0;a<r;a++)o[a]=arguments[a];var i=[].concat(o);e&&-1===i.indexOf(e)&&i.push(e),n.apply(this,i),t.apply(this,i)}}),(function(){}))}var G="undefined"!=typeof window?r.useLayoutEffect:r.useEffect;function F(n){var e=(0,r.useRef)(n);return G((function(){e.current=n})),(0,r.useCallback)((function(){return e.current.apply(void 0,arguments)}),[])}var J=(0,r.forwardRef)((function(n,e){var t=n.children,o=n.autoHideDuration,a=n.ClickAwayListenerProps,i=n.disableWindowBlurListener,c=void 0!==i&&i,s=n.onClose,u=n.onMouseEnter,l=n.onMouseLeave,d=n.open,f=n.resumeHideDuration,p=E(n,["children","autoHideDuration","ClickAwayListenerProps","disableWindowBlurListener","onClose","onMouseEnter","onMouseLeave","open","resumeHideDuration"]),g=(0,r.useRef)(),h=F((function(){s&&s.apply(void 0,arguments)})),k=F((function(n){s&&null!=n&&(clearTimeout(g.current),g.current=setTimeout((function(){h(null,A.TIMEOUT)}),n))}));(0,r.useEffect)((function(){return d&&k(o),function(){clearTimeout(g.current)}}),[d,o,k]);var b=function(){clearTimeout(g.current)},x=(0,r.useCallback)((function(){null!=o&&k(null!=f?f:.5*o)}),[o,f,k]);return(0,r.useEffect)((function(){if(!c&&d)return window.addEventListener("focus",x),window.addEventListener("blur",b),function(){window.removeEventListener("focus",x),window.removeEventListener("blur",b)}}),[c,x,d]),(0,r.createElement)(m.Z,v({onClickAway:function(n){s&&s(n,A.CLICKAWAY)}},a),(0,r.createElement)("div",v({onMouseEnter:function(n){u&&u(n),b()},onMouseLeave:function(n){l&&l(n),x()},ref:e},p),t))})),$=function(n){var e,t=n.palette.mode||n.palette.type,r=(0,l._4)(n.palette.background.default,"light"===t?.8:.98);return(0,s.Z)(v({},x,{lessPadding:{paddingLeft:20},variantSuccess:{backgroundColor:"#43a047 !important",color:"#fff !important"},variantError:{backgroundColor:"#d32f2f !important",color:"#fff !important"},variantInfo:{backgroundColor:"#2196f3 !important",color:"#fff !important"},variantWarning:{backgroundColor:"#ff9800 !important",color:"#fff !important"},contentRoot:v({},n.typography.body2,{backgroundColor:r,color:n.palette.getContrastText(r),alignItems:"center",padding:"6px 16px",borderRadius:"4px",boxShadow:"0px 3px 5px -1px rgba(0,0,0,0.2),0px 6px 10px 0px rgba(0,0,0,0.14),0px 1px 18px 0px rgba(0,0,0,0.12)"}),message:{display:"flex",alignItems:"center",padding:"8px 0"},action:{display:"flex",alignItems:"center",marginLeft:"auto",paddingLeft:16,marginRight:-8},wrappedRoot:{position:"relative",transform:"translateX(0)",top:0,right:0,bottom:0,left:0},collapseContainer:(e={},e[n.breakpoints.down("xs")]={paddingLeft:n.spacing(1),paddingRight:n.spacing(1)},e),collapseWrapper:{transition:n.transitions.create(["margin-bottom"],{easing:"ease"}),marginTop:w,marginBottom:w},collapseWrapperDense:{marginTop:S,marginBottom:S},collapseWrapperInner:{width:"auto"}}))},nn=function(n){var e=n.classes,t=E(n,["classes"]),a=(0,r.useRef)(),c=(0,r.useState)(!0),s=c[0],u=c[1];(0,r.useEffect)((function(){return function(){a.current&&clearTimeout(a.current)}}),[]);var l=Y([t.snack.onClose,t.onClose],t.snack.key),d=t.style,p=t.dense,m=t.ariaAttributes,g=t.className,h=t.hideIconVariant,k=t.iconVariant,b=t.snack,x=t.action,C=t.content,y=t.TransitionComponent,O=t.TransitionProps,w=t.transitionDuration,S=E(t,["style","dense","ariaAttributes","className","hideIconVariant","iconVariant","snack","action","content","TransitionComponent","TransitionProps","transitionDuration","onEnter","onEntered","onEntering","onExit","onExited","onExiting"]),L=b.key,T=b.open,I=b.className,M=b.variant,N=b.content,V=b.action,j=b.ariaAttributes,Z=b.anchorOrigin,B=b.message,_=b.TransitionComponent,U=b.TransitionProps,K=b.transitionDuration,X=E(b,["persist","key","open","entered","requestClose","className","variant","content","action","ariaAttributes","anchorOrigin","message","TransitionComponent","TransitionProps","transitionDuration","onEnter","onEntered","onEntering","onExit","onExited","onExiting"]),G=v({},Q,{},k)[M],F=v({"aria-describedby":"notistack-snackbar"},q(j,m)),$=_||y||D.TransitionComponent,nn=q(K,w,D.transitionDuration),en=v({direction:P(Z)},q(U,O)),tn=V||x;"function"==typeof tn&&(tn=tn(L));var rn=N||C;"function"==typeof rn&&(rn=rn(L,b.message));var on=["onEnter","onEntering","onEntered","onExit","onExiting","onExited"].reduce((function(n,e){var r;return v({},n,((r={})[e]=Y([t.snack[e],t[e]],t.snack.key),r))}),{});return o().createElement(f.Z,{unmountOnExit:!0,timeout:175,in:s,classes:z(e,p),onExited:on.onExited},o().createElement(J,Object.assign({},S,X,{open:T,className:(0,i.default)(e.root,e.wrappedRoot,e[R(Z)]),onClose:l}),o().createElement($,Object.assign({appear:!0,in:T,timeout:nn},en,{onExit:on.onExit,onExiting:on.onExiting,onExited:function(){a.current=setTimeout((function(){u(!s)}),125)},onEnter:on.onEnter,onEntering:on.onEntering,onEntered:Y([on.onEntered,function(){t.snack.requestClose&&l(null,A.INSTRCUTED)}])}),rn||o().createElement(W,Object.assign({},F,{role:"alert",style:d,className:(0,i.default)(e.contentRoot,e[H(M)],g,I,!h&&G&&e.lessPadding)}),o().createElement("div",{id:F["aria-describedby"],className:e.message},h?null:G,B),tn&&o().createElement("div",{className:e.action},tn)))))},en=(0,u.Z)($)(nn),tn=(0,d.Z)((function(n){var e,t;return{root:(e={boxSizing:"border-box",display:"flex",maxHeight:"100%",maxWidth:"100%",position:"fixed",flexDirection:"column",zIndex:n.zIndex.snackbar,height:"auto",width:"auto",minWidth:288,transition:n.transitions.create(["top","right","bottom","left"],{easing:"ease"})},e[n.breakpoints.down("xs")]={left:"0 !important",right:"0 !important",width:"100%"},e),reverseColumns:{flexDirection:"column-reverse"},top:{top:y-w},topDense:{top:O-S},bottom:{bottom:y-w},bottomDense:{bottom:O-S},left:{left:y},leftDense:{left:O},right:{right:y},rightDense:{right:O},center:(t={left:"50%",transform:"translateX(-50%)"},t[n.breakpoints.down("xs")]={transform:"translateX(0)"},t)}})),rn=function(n){var e=tn(),t=n.className,r=n.anchorOrigin,a=n.dense,c=E(n,["className","anchorOrigin","dense"]),s=(0,i.default)(e.root,e[r.vertical],e[r.horizontal],e[r.vertical+(a?"Dense":"")],e[r.horizontal+(a?"Dense":"")],t,"bottom"===r.vertical&&e.reverseColumns);return o().createElement("div",Object.assign({className:s},c))},on=o().memo(rn),an=function(n){var e,t,r,c;function s(e){var t;return(t=n.call(this,e)||this).enqueueSnackbar=function(n,e){void 0===e&&(e={});var r=e,o=r.key,a=r.preventDuplicate,i=E(r,["key","preventDuplicate"]),c=M(o),s=c?o:(new Date).getTime()+Math.random(),u=function(n,e,t){return function(r){return"autoHideDuration"===r?N(n.autoHideDuration)?n.autoHideDuration:N(e.autoHideDuration)?e.autoHideDuration:D.autoHideDuration:n[r]||e[r]||t[r]}}(i,t.props,D),l=v({key:s},i,{message:n,open:!0,entered:!1,requestClose:!1,variant:u("variant"),anchorOrigin:u("anchorOrigin"),autoHideDuration:u("autoHideDuration")});return i.persist&&(l.autoHideDuration=void 0),t.setState((function(e){if(void 0===a&&t.props.preventDuplicate||a){var r=function(e){return c?e.key===o:e.message===n},i=e.queue.findIndex(r)>-1,s=e.snacks.findIndex(r)>-1;if(i||s)return e}return t.handleDisplaySnack(v({},e,{queue:[].concat(e.queue,[l])}))})),s},t.handleDisplaySnack=function(n){return n.snacks.length>=t.maxSnack?t.handleDismissOldest(n):t.processQueue(n)},t.processQueue=function(n){var e=n.queue,t=n.snacks;return e.length>0?v({},n,{snacks:[].concat(t,[e[0]]),queue:e.slice(1,e.length)}):n},t.handleDismissOldest=function(n){if(n.snacks.some((function(n){return!n.open||n.requestClose})))return n;var e=!1,r=!1;n.snacks.reduce((function(n,e){return n+(e.open&&e.persist?1:0)}),0)===t.maxSnack&&(r=!0);var o=n.snacks.map((function(n){return e||n.persist&&!r?v({},n):(e=!0,n.entered?(n.onClose&&n.onClose(null,A.MAXSNACK,n.key),t.props.onClose&&t.props.onClose(null,A.MAXSNACK,n.key),v({},n,{open:!1})):v({},n,{requestClose:!0}))}));return v({},n,{snacks:o})},t.handleEnteredSnack=function(n,e,r){if(!M(r))throw new Error("handleEnteredSnack Cannot be called with undefined key");t.setState((function(n){return{snacks:n.snacks.map((function(n){return n.key===r?v({},n,{entered:!0}):v({},n)}))}}))},t.handleCloseSnack=function(n,e,r){if(t.props.onClose&&t.props.onClose(n,e,r),e!==A.CLICKAWAY){var o=void 0===r;t.setState((function(n){var e=n.snacks,t=n.queue;return{snacks:e.map((function(n){return o||n.key===r?n.entered?v({},n,{open:!1}):v({},n,{requestClose:!0}):v({},n)})),queue:t.filter((function(n){return n.key!==r}))}}))}},t.closeSnackbar=function(n){var e=t.state.snacks.find((function(e){return e.key===n}));M(n)&&e&&e.onClose&&e.onClose(null,A.INSTRUCTED,n),t.handleCloseSnack(null,A.INSTRUCTED,n)},t.handleExitedSnack=function(n,e,r){var o=e||r;if(!M(o))throw new Error("handleExitedSnack Cannot be called with undefined key");t.setState((function(n){var e=t.processQueue(v({},n,{snacks:n.snacks.filter((function(n){return n.key!==o}))}));return 0===e.queue.length?e:t.handleDismissOldest(e)}))},t.state={snacks:[],queue:[],contextValue:{enqueueSnackbar:t.enqueueSnackbar,closeSnackbar:t.closeSnackbar}},t}return t=n,(e=s).prototype=Object.create(t.prototype),e.prototype.constructor=e,e.__proto__=t,s.prototype.render=function(){var n=this,e=this.state.contextValue,t=this.props,r=t.iconVariant,c=t.dense,s=void 0===c?D.dense:c,u=t.hideIconVariant,l=void 0===u?D.hideIconVariant:u,d=t.domRoot,f=t.children,p=t.classes,m=void 0===p?{}:p,g=E(t,["maxSnack","preventDuplicate","variant","anchorOrigin","iconVariant","dense","hideIconVariant","domRoot","children","classes"]),h=this.state.snacks.reduce((function(n,e){var t,r,o=(r=e.anchorOrigin,""+L(r.vertical)+L(r.horizontal)),a=n[o]||[];return v({},n,((t={})[o]=[].concat(a,[e]),t))}),{}),k=Object.keys(h).map((function(e){var t=h[e];return o().createElement(on,{key:e,dense:s,anchorOrigin:t[0].anchorOrigin,className:(0,i.default)(m.containerRoot,m[I(e)])},t.map((function(e){return o().createElement(en,Object.assign({},g,{key:e.key,snack:e,dense:s,iconVariant:r,hideIconVariant:l,classes:T(m),onClose:n.handleCloseSnack,onExited:Y([n.handleExitedSnack,n.props.onExited]),onEntered:Y([n.handleEnteredSnack,n.props.onEntered])}))})))}));return o().createElement(b.Provider,{value:e},f,d?(0,a.createPortal)(k,d):k)},r=s,(c=[{key:"maxSnack",get:function(){return this.props.maxSnack||D.maxSnack}}])&&k(r.prototype,c),s}(r.Component),cn=function(n){var e=o().forwardRef((function(e,t){return o().createElement(b.Consumer,null,(function(r){return o().createElement(n,v({},e,{ref:t,enqueueSnackbar:r.enqueueSnackbar,closeSnackbar:r.closeSnackbar}))}))}));return h()(e,n),e},sn=function(){return(0,r.useContext)(b)}}}]);