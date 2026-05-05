import { Scene } from 'phaser';

export class MainMenu extends Scene {
    constructor() {
        super('MainMenu');
    }

    create() {
        this.cameras.main.setBackgroundColor('#9d505d');

        this.add.image(400, 300, 'background').setAlpha(0.3);

        const title = this.add.text(400, 100, 'MOUSE CATCHERS', {
            fontFamily: 'Arial Black', fontSize: '48px', color: '#ffb6c1',
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);


        const winner = localStorage.getItem('lastWinner');
        const s1 = localStorage.getItem('score1');
        const s2 = localStorage.getItem('score2');


        const startText = this.add.text(400, 350, 'START', {
            fontFamily: 'Arial Black', fontSize: '50px', color: '#ffb6c1',
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);

        this.tweens.add({
            targets: startText,
            scaleX: 1.1,
            scaleY: 1.1,
            duration: 800,
            yoyo: true,
            repeat: -1
        });

        this.input.once('pointerdown', () => {
            this.scene.start('Game');
        });
    }
}