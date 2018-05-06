/**
 * 3d模型管理
 *
 * @authod 王耕
 * @time 2015-7-13
 */

var container, stats;
var camera, scene, renderer;
var controls, cross;

var DIMENSIONAL_FIELD = {
    execute: function (path) {
        if (!Detector.webgl) Detector.addGetWebGLMessage();

        DIMENSIONAL_FIELD.init(path);
        DIMENSIONAL_FIELD.animate();
    },
    init: function (path) {

        //renderer
        renderer = new THREE.WebGLRenderer({antialias: false});
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth * 0.8, window.innerHeight * 0.8);
        renderer.sortObjects = false;
        document.body.appendChild(renderer.domElement);

        camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 10, 2000);
        camera.position.z = 200;

        controls = new THREE.TrackballControls(camera, renderer.domElement);

        controls.rotateSpeed = 5.0;
        controls.zoomSpeed = 5;
        controls.panSpeed = 2;

        controls.noZoom = false;
        controls.noPan = false;

        controls.staticMoving = true;
        controls.dynamicDampingFactor = 0.3;

        scene = new THREE.Scene();

        scene.add(camera);

        //light
        DIMENSIONAL_FIELD.addLight(0x757575, 0xFFFFFF, scene, 0, 0, 10);

        var loader = new THREE.OBJLoader();
        DIMENSIONAL_FIELD.addModelToScene(loader, path);


        container = document.getElementById('view');
        container.appendChild(renderer.domElement);

        stats = new Stats();
        stats.domElement.style.position = 'absolute';
        stats.domElement.style.top = '80px';
//        stats.domElement.style.position = 'absolute';
//        stats.domElement.style.top = '0px';
//        stats.domElement.style.left = '0px';
        container.appendChild(stats.domElement);

        window.addEventListener('resize', DIMENSIONAL_FIELD.onWindowResize, false);
    },
    animate: function () {
        requestAnimationFrame(DIMENSIONAL_FIELD.animate);
        controls.update();
        renderer.render(scene, camera);
        stats.update();
    },
    onWindowResize: function () {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
        controls.handleResize();
    },
    addLight: function (color1, color2, scene, x, y, z) {
        var ambient = new THREE.AmbientLight(color1);
        ambient.position.set(x, y, z);
        scene.add(ambient);


        var directionalLight = new THREE.DirectionalLight(color1);
        directionalLight.position.set(x, y, z);
        scene.add(directionalLight);
    },
    addModelToScene: function (loader, path) {
        loader.load(path, function (geometry) {
            var material = geometry.children[0].material;
            if (material instanceof THREE.MeshLambertMaterial) {
                material.transparent = true;
                material.opacity = 0.9;
            }
            scene.add(geometry);
        });
    }
};