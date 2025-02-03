export default [
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },
  { path: '/welcome', name: '欢迎', icon: 'smile', component: './Welcome' },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { name: '接口管理', icon: 'table', path: '/admin/api', component: './Admin/ApiInfo' },
      { path: '/admin', redirect: '/admin/sub-page' },
    ],
  },
  // { path: '/', redirect: '/welcome' },
  { path: '/', redirect: '/admin/api' },
  { path: '*', layout: false, component: './404' },
];
