import Phaser from 'phaser';

export class Game extends Phaser.Scene {
    constructor() {
        super('Game');
    }

    create() {
        this.speed1 = 300;
        this.speed2 = 300;
        this.isSlowed1 = false;
        this.isSlowed2 = false;

        this.player1 = this.add.text(200, 300, '🐱', { fontSize: '50px' }).setOrigin(0.5);        this.physics.add.existing(this.player1);
        this.player1.body.setCircle(25);
        this.player1.body.setCollideWorldBounds(true);

        this.player2 = this.add.text(600, 300, '🦊', { fontSize: '50px' }).setOrigin(0.5);
        this.physics.add.existing(this.player2);
        this.player2.body.setCircle(25);
        this.player2.body.setCollideWorldBounds(true);

        this.cursors = this.input.keyboard.createCursorKeys();
        this.wasd = this.input.keyboard.addKeys({
            up: Phaser.Input.Keyboard.KeyCodes.W,
            down: Phaser.Input.Keyboard.KeyCodes.S,
            left: Phaser.Input.Keyboard.KeyCodes.A,
            right: Phaser.Input.Keyboard.KeyCodes.D
        });

        this.score1 = 0;
        this.score2 = 0;

        this.scoreText1 = this.add.text(16, 16, 'Cat: 0', { fontFamily: 'Arial Black', fontSize: '20px', fill: '#c44343', stroke: '#ffffff', strokeThickness: 3 });
        this.scoreText2 = this.add.text(600, 16, 'Fox: 0', { fontFamily: 'Arial Black', fontSize: '20px', fill: '#486bc5', stroke: '#ffffff', strokeThickness: 3 });

        this.target = this.add.text(400, 300, '🐭', { fontSize: '40px' }).setOrigin(0.5);
        this.physics.add.existing(this.target);
        this.target.body.setCircle(25);

        this.tweens.add({
            targets: this.target,
            angle: 360,
            duration: 2000,
            repeat: -1
        });

        this.obstacles = this.physics.add.group();

        this.spawnObstacle();

        this.physics.add.collider(this.player1, this.player2);

        this.physics.add.overlap(this.player1, this.target, this.collectTarget1, null, this);
        this.physics.add.overlap(this.player2, this.target, this.collectTarget2, null, this);

        this.physics.add.collider(this.player1, this.obstacles, this.hitObstacle1, null, this);
        this.physics.add.collider(this.player2, this.obstacles, this.hitObstacle2, null, this);
    }

    spawnObstacle() {
        const x = Phaser.Math.Between(100, 700);
        const y = Phaser.Math.Between(50, 150);

        let obstacle = this.add.text(x, y, '🐶', { fontSize: '40px' }).setOrigin(0.5);

        this.obstacles.add(obstacle);

        obstacle.body.setCircle(20);
        obstacle.body.setCollideWorldBounds(true);
        obstacle.body.setBounce(1, 1);

        let dirX = Phaser.Math.Between(0, 1) === 0 ? -1 : 1;
        let dirY = Phaser.Math.Between(0, 1) === 0 ? -1 : 1;

        let velocityX = Phaser.Math.Between(200, 400) * dirX;
        let velocityY = Phaser.Math.Between(300, 500) * dirY;

        obstacle.body.setVelocity(velocityX, velocityY);
    }

    checkObstacles() {
        let maxScore = Math.max(this.score1, this.score2);
        let currentObstacles = this.obstacles.getChildren().length;

        if (maxScore >= 40 && currentObstacles === 1) {
            this.spawnObstacle();
        } else if (maxScore >= 80 && currentObstacles === 2) {
            this.spawnObstacle();
        }
    }

    collectTarget1(player, target) {
        this.score1 += 10;
        this.scoreText1.setText('Cat: ' + this.score1);
        this.repositionTarget();
        this.checkObstacles();
        this.checkWinCondition();
    }

    collectTarget2(player, target) {
        this.score2 += 10;
        this.scoreText2.setText('Fox: ' + this.score2);
        this.repositionTarget();
        this.checkObstacles();
        this.checkWinCondition();
    }

    hitObstacle1(player, obstacle) {
        if (this.isSlowed1) return;

        this.isSlowed1 = true;
        this.speed1 = 100;
        this.player1.setAlpha(0.4);

        this.time.delayedCall(5000, () => {
            this.speed1 = 300;
            this.isSlowed1 = false;
            this.player1.setAlpha(1);
        });
    }

    hitObstacle2(player, obstacle) {
        if (this.isSlowed2) return;

        this.isSlowed2 = true;
        this.speed2 = 100;
        this.player2.setAlpha(0.4);

        this.time.delayedCall(5000, () => {
            this.speed2 = 300;
            this.isSlowed2 = false;
            this.player2.setAlpha(1);
        });
    }

    repositionTarget() {
        const x = Phaser.Math.Between(50, 750);
        const y = Phaser.Math.Between(50, 550);
        this.target.setPosition(x, y);
    }

    checkWinCondition() {
        if (this.score1 >= 100 || this.score2 >= 100) {
            let winner = this.score1 >= 100 ? 'Cat' : 'Fox';

            localStorage.setItem('lastWinner', winner);
            localStorage.setItem('score1', this.score1);
            localStorage.setItem('score2', this.score2);

            this.scene.start('GameOver');
        }
    }

    update() {
        this.player1.body.setVelocity(0);
        this.player2.body.setVelocity(0);

        if (this.wasd.left.isDown) {
            this.player1.body.setVelocityX(-this.speed1);
        } else if (this.wasd.right.isDown) {
            this.player1.body.setVelocityX(this.speed1);
        }

        if (this.wasd.up.isDown) {
            this.player1.body.setVelocityY(-this.speed1);
        } else if (this.wasd.down.isDown) {
            this.player1.body.setVelocityY(this.speed1);
        }

        if (this.cursors.left.isDown) {
            this.player2.body.setVelocityX(-this.speed2);
        } else if (this.cursors.right.isDown) {
            this.player2.body.setVelocityX(this.speed2);
        }

        if (this.cursors.up.isDown) {
            this.player2.body.setVelocityY(-this.speed2);
        } else if (this.cursors.down.isDown) {
            this.player2.body.setVelocityY(this.speed2);
        }
    }
}