(self.webpackChunk_unified_campaign=self.webpackChunk_unified_campaign||[]).push([[1858],{12897:(e,t,n)=>{e.exports={parse:n(41944),stringify:n(50984)}},698:(e,t,n)=>{var r=/([\w-]+)|=|(['"])([.\s\S]*?)\2/g,i=n(64896);e.exports=function(e){var t,n=0,a=!0,c={type:"tag",name:"",voidElement:!1,attrs:{},children:[]};return e.replace(r,(function(r){if("="===r)return a=!0,void n++;a?0===n?((i[r]||"/"===e.charAt(e.length-2))&&(c.voidElement=!0),c.name=r):(c.attrs[t]=r.replace(/^['"]|['"]$/g,""),t=void 0):(t&&(c.attrs[t]=t),t=r),n++,a=!1})),c}},41944:(e,t,n)=>{var r=/(?:<!--[\S\s]*?-->|<(?:"[^"]*"['"]*|'[^']*'['"]*|[^'">])+>)/g,i=n(698),a=Object.create?Object.create(null):{};function c(e,t,n,r,i){var a=t.indexOf("<",r),c=t.slice(r,-1===a?void 0:a);/^\s*$/.test(c)&&(c=" "),(!i&&a>-1&&n+e.length>=0||" "!==c)&&e.push({type:"text",content:c})}e.exports=function(e,t){t||(t={}),t.components||(t.components=a);var n,o=[],s=-1,u=[],p={},h=!1;return e.replace(r,(function(r,a){if(h){if(r!=="</"+n.name+">")return;h=!1}var l,d="/"!==r.charAt(1),f=0===r.indexOf("\x3c!--"),g=a+r.length,m=e.charAt(g);d&&!f&&(s++,"tag"===(n=i(r)).type&&t.components[n.name]&&(n.type="component",h=!0),n.voidElement||h||!m||"<"===m||c(n.children,e,s,g,t.ignoreWhitespace),p[n.tagName]=n,0===s&&o.push(n),(l=u[s-1])&&l.children.push(n),u[s]=n),(f||!d||n.voidElement)&&(f||s--,!h&&"<"!==m&&m&&c(l=-1===s?o:u[s].children,e,s,g,t.ignoreWhitespace))})),!o.length&&e.length&&c(o,e,0,0,t.ignoreWhitespace),o}},50984:e=>{function t(e,n){switch(n.type){case"text":return e+n.content;case"tag":return e+="<"+n.name+(n.attrs?function(e){var t=[];for(var n in e)t.push(n+'="'+e[n]+'"');return t.length?" "+t.join(" "):""}(n.attrs):"")+(n.voidElement?"/>":">"),n.voidElement?e:e+n.children.reduce(t,"")+"</"+n.name+">"}}e.exports=function(e){return e.reduce((function(e,n){return e+t("",n)}),"")}}}]);