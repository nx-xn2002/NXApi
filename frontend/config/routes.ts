export default [
  { path: '/', name: '主页', icon: 'smile', component: './Index' },
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },
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
  { path: '*', layout: false, component: './404' },
];
