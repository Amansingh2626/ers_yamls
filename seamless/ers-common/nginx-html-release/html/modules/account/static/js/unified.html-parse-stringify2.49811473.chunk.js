(self.webpackChunk_unified_account=self.webpackChunk_unified_account||[]).push([[1858],{12897:(e,t,n)=>{e.exports={parse:n(41944),stringify:n(50984)}},698:(e,t,n)=>{var r=/([\w-]+)|=|(['"])([.\s\S]*?)\2/g,c=n(64896);e.exports=function(e){var t,n=0,i=!0,a={type:"tag",name:"",voidElement:!1,attrs:{},children:[]};return e.replace(r,(function(r){if("="===r)return i=!0,void n++;i?0===n?((c[r]||"/"===e.charAt(e.length-2))&&(a.voidElement=!0),a.name=r):(a.attrs[t]=r.replace(/^['"]|['"]$/g,""),t=void 0):(t&&(a.attrs[t]=t),t=r),n++,i=!1})),a}},41944:(e,t,n)=>{var r=/(?:<!--[\S\s]*?-->|<(?:"[^"]*"['"]*|'[^']*'['"]*|[^'">])+>)/g,c=n(698),i=Object.create?Object.create(null):{};function a(e,t,n,r,c){var i=t.indexOf("<",r),a=t.slice(r,-1===i?void 0:i);/^\s*$/.test(a)&&(a=" "),(!c&&i>-1&&n+e.length>=0||" "!==a)&&e.push({type:"text",content:a})}e.exports=function(e,t){t||(t={}),t.components||(t.components=i);var n,o=[],s=-1,u=[],p={},h=!1;return e.replace(r,(function(r,i){if(h){if(r!=="</"+n.name+">")return;h=!1}var l,d="/"!==r.charAt(1),f=0===r.indexOf("\x3c!--"),g=i+r.length,m=e.charAt(g);d&&!f&&(s++,"tag"===(n=c(r)).type&&t.components[n.name]&&(n.type="component",h=!0),n.voidElement||h||!m||"<"===m||a(n.children,e,s,g,t.ignoreWhitespace),p[n.tagName]=n,0===s&&o.push(n),(l=u[s-1])&&l.children.push(n),u[s]=n),(f||!d||n.voidElement)&&(f||s--,!h&&"<"!==m&&m&&a(l=-1===s?o:u[s].children,e,s,g,t.ignoreWhitespace))})),!o.length&&e.length&&a(o,e,0,0,t.ignoreWhitespace),o}},50984:e=>{function t(e,n){switch(n.type){case"text":return e+n.content;case"tag":return e+="<"+n.name+(n.attrs?function(e){var t=[];for(var n in e)t.push(n+'="'+e[n]+'"');return t.length?" "+t.join(" "):""}(n.attrs):"")+(n.voidElement?"/>":">"),n.voidElement?e:e+n.children.reduce(t,"")+"</"+n.name+">"}}e.exports=function(e){return e.reduce((function(e,n){return e+t("",n)}),"")}}}]);