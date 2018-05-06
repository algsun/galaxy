/**
 * 3d模型管理
 *
 * @authod 王耕
 * @time 2015-6-11
 */

var container, stats;
var camera, scene, renderer;
var controls, cross;

var containerTag;
var rendererTag, cameraTag, sceneTag;
var objects = [];

var raycaster;
var mouse;

var num = 25;
var colorMaping = {};
var DIMENSIONAL_FIELD = {
    execute: function (path, dimensionalId, stamp, submit, sensorId) {
        if (!Detector.webgl) Detector.addGetWebGLMessage();

        raycaster = new THREE.Raycaster();
        mouse = new THREE.Vector2();

        DIMENSIONAL_FIELD.init(path, dimensionalId, stamp, submit, sensorId);
        DIMENSIONAL_FIELD.animate();
    },
    init: function (path, dimensionalId, stamp, submit, sensorId) {

        //renderer
        renderer = new THREE.WebGLRenderer({antialias: false});
        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth * 0.7, window.innerHeight * 0.7);
        renderer.sortObjects = false;
        document.body.appendChild(renderer.domElement);

        camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 10, 2000);
        camera.position.z = 250;


        cameraTag = new THREE.PerspectiveCamera(60, 1, 10, 2000);
        cameraTag.position.z = 250;

        controls = new THREE.TrackballControls(camera, renderer.domElement);

        controls.rotateSpeed = 5.0;
        controls.zoomSpeed = 5;
        controls.panSpeed = 2;

        controls.noZoom = false;
        controls.noPan = false;

        controls.staticMoving = true;
        controls.dynamicDampingFactor = 0.3;

        scene = new THREE.Scene();
        sceneTag = new THREE.Scene();

        scene.add(camera);
        sceneTag.add(cameraTag);

        //light
        DIMENSIONAL_FIELD.addLight(0xffffff, 0xffeedd, scene, 0, 0, 10);
        DIMENSIONAL_FIELD.addLight(0xffffff, 0xffeedd, sceneTag, 0, 0, 10);

        var loader = new THREE.OBJLoader();
        DIMENSIONAL_FIELD.addModelToScene(loader, path);
        DIMENSIONAL_FIELD.refreshJsonData(submit, stamp, dimensionalId, sensorId, 0);
        DIMENSIONAL_FIELD.initGUI(submit, stamp, dimensionalId, sensorId);

        //rendererTag
        rendererTag = new THREE.WebGLRenderer({antialias: true});
        rendererTag.setPixelRatio(2);
        rendererTag.setSize(window.innerHeight * 0.7, window.innerHeight * 0.7);

        container = document.getElementById('view');
        container.appendChild(renderer.domElement);

        containerTag = document.getElementById('tag');
        containerTag.appendChild(rendererTag.domElement);


        stats = new Stats();
        stats.domElement.style.position = 'absolute';
        stats.domElement.style.top = '160px';
        container.appendChild(stats.domElement);

        window.addEventListener('resize', DIMENSIONAL_FIELD.onWindowResize, false);
        document.addEventListener('mousemove', DIMENSIONAL_FIELD.onDocumentMouseOver, false);
    },
    onDocumentMouseOver: function (event) {
        event.preventDefault();

        var top = document.getElementById("tag").offsetTop;
        var left = document.getElementById("tag").offsetLeft;

        mouse.x = ( (event.clientX - left) / (window.innerHeight * 0.7) ) * 2 - 1;
        mouse.y = -( (event.clientY - top) / (window.innerHeight * 0.7) ) * 2 + 1;
    },

    animate: function () {
        requestAnimationFrame(DIMENSIONAL_FIELD.animate);
        DIMENSIONAL_FIELD.render();
        stats.update();
    },
    render: function () {
        raycaster.setFromCamera(mouse, cameraTag);
        var intersects = raycaster.intersectObjects(sceneTag.children);
        if (intersects.length > 0) {
            if (intersects[0].object.geometry instanceof THREE.BoxGeometry) {


                var color = intersects[0].object.material.color;

                for (var key in colorMaping) {
                    var mapColor = colorMaping[key];
                    if (mapColor == "rgb(" + color.r * 255 + ",0," + color.b * 255 + ")") {
                        $("#colorValue").val(key);
                    }
                }
            }
        }
        controls.update();
        renderer.render(scene, camera);
        rendererTag.render(sceneTag, cameraTag);
    },
    onWindowResize: function () {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        cameraTag.aspect = 1;
        cameraTag.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
        rendererTag.setSize(window.innerHeight * 0.7, window.innerHeight * 0.7);
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
    initGUI: function (submit, stamp, dimensionalId, sensorId) {
        var cube = DIMENSIONAL_FIELD.addCubeToScene(0xff0000, 200, 0, 200, 0, 0, 0, scene);
        $("#range").mouseup(function () {
            var rangeValue = $("#range").val();
            DIMENSIONAL_FIELD.refreshJsonData(submit, stamp, dimensionalId, sensorId, rangeValue);
            $.each(scene.children, function (index, item) {
                if (item instanceof THREE.Mesh) {
                    item.remove();
                }
            });
            cube.position.y = rangeValue;
            scene.add(cube);
        });
    },
    refreshJsonData: function (submit, stamp, dimensionalId, sensorId, realPointZ) {
        $.getJSON("three-dimensional/disposePoints.json", {submit: submit, stamp: stamp, dimensionalId: dimensionalId, sensorId: sensorId}, function (result) {
            var max = DIMENSIONAL_FIELD.maxValue(result);
            var min = DIMENSIONAL_FIELD.minValue(result);
            DIMENSIONAL_FIELD.createText(max, 10, 3, -118, 106, 0);
            DIMENSIONAL_FIELD.createText(min, 10, 3, -118, -115, 0);
            DIMENSIONAL_FIELD.addTagAndColorField(max, min, realPointZ, result);
        });
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
    },
    addTagAndColorField: function (max, min, realPointZ, result) {
        DIMENSIONAL_FIELD.addTag(max, min);
        DIMENSIONAL_FIELD.addColorField(max, min, realPointZ, result);

    },
    addTag: function (max, min) {
        for (var i = 0; i <= num; i++) {
            var color = DIMENSIONAL_FIELD.colorMap(i, max, min);
            for (var j = 0; j <= 1; j++) {
                var x = -115 + j * 8;
                var y = 100 - i * 8;
                var z = 0;
                DIMENSIONAL_FIELD.addCubeToScene(color, 8, 8, 0, x, y, z, sceneTag);
            }
        }
    },
    addColorField: function (max, min, realPointZ, result) {
        for (var i = 0; i <= num; i++) {
            for (var j = 0; j <= num; j++) {
                var crossColor = DIMENSIONAL_FIELD.crossColorMap(max, min, -100 + i * 8, -100 + j * 8, realPointZ, result);
                var x = -90 + i * 8;
                var y = 100 - j * 8;
                var z = 0;
                var tagCube = DIMENSIONAL_FIELD.addCubeToScene(crossColor, 8, 8, 0, x, y, z, sceneTag);
                objects.push(tagCube);
            }
        }
    },
    addCubeToScene: function (color, sizeX, sizeY, sizeZ, x, y, z, scene) {
        var geometry = new THREE.BoxGeometry(sizeX, sizeY, sizeZ);
        var material = new THREE.MeshBasicMaterial({color: color});
        var cube = new THREE.Mesh(geometry, material);
        cube.position.x = x;
        cube.position.y = y;
        cube.position.z = z;
        scene.add(cube);
        return cube;
    },
    maxValue: function (data) {
        var max = data[0].locationDataVO.sensorPhysicalValue;
        for (var i = 0; i < data.length; i++) {
            var deviceData = data[i];
            var value = deviceData.locationDataVO.sensorPhysicalValue;
            if (value > max) {
                max = value;
            }
        }
        return max;
    },
    minValue: function (data) {
        var min = data[0].locationDataVO.sensorPhysicalValue;
        for (var i = 0; i < data.length; i++) {
            var deviceData = data[i];
            var value = deviceData.locationDataVO.sensorPhysicalValue;
            if (value < min) {
                min = value;
            }
        }
        return min;
    },
    colorMap: function (k, max, min) {

        var colorStep = 255 / num;
        var stepIndex = (k * colorStep).toFixed(0);
        var a = 255 - stepIndex;
        var colorStr = "rgb(" + a + ",0," + stepIndex + ")";

        var valueStep = (max - min) / num;
        var value = max - valueStep * k;

        colorMaping[value.toFixed(2)] = colorStr;

        return new THREE.Color(colorStr);
    },
    crossColorMap: function (max, min, x, y, z, data) {
        var color;
        //权值和
        var wSum = 0;
        //权值与实际值乘积和
        var wvSum = 0;
        var valueStep = (max - min) / num;
        for (var i = 0; i < data.length; i++) {
            var deviceData = data[i];
            var value = deviceData.locationDataVO.sensorPhysicalValue;
            var locationX = deviceData.coordinateX;
            var locationY = deviceData.coordinateY;
            var locationZ = deviceData.coordinateZ;
            var w = Math.pow(((x - locationX) * (x - locationX) + (y - locationY) * (y - locationY) + (z - locationZ) * (z - locationZ)), 0.5);
            // 累加权值和
            wSum = wSum + w;
            // 计算权值 * 实际数据  并累加
            wvSum = wvSum + w * value;
        }
        var result = (wvSum / wSum).toFixed(1);
        for (key in colorMaping) {
            if (Math.abs(key - result) <= valueStep) {
                color = colorMaping[key];
            }
        }
        return color;
    },
    createText: function (text, size, height, x, y, z) {
        var material = new THREE.MeshFaceMaterial([
            new THREE.MeshPhongMaterial({ color: 0xffffff, shading: THREE.FlatShading }), // front
            new THREE.MeshPhongMaterial({ color: 0xffffff, shading: THREE.SmoothShading }) // side
        ]);
        var textGeo = new THREE.TextGeometry(text, {
            size: size,
            height: height
        });

        textGeo.computeBoundingBox();
        textGeo.computeVertexNormals();
        var textMesh1 = new THREE.Mesh(textGeo, material);
        textMesh1.position.x = x;
        textMesh1.position.y = y;
        textMesh1.position.z = z;
        sceneTag.add(textMesh1);
    }
};