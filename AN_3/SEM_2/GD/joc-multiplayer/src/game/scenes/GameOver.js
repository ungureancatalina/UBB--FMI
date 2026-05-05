import { Scene } from 'phaser';

export class GameOver extends Scene {
    constructor() {
        super('GameOver');
    }

    create() {
        const winner = localStorage.getItem('lastWinner') || 'Nobody';
        const s1 = localStorage.getItem('score1') || 0;
        const s2 = localStorage.getItem('score2') || 0;

        this.cameras.main.setBackgroundColor('#a26872');

        this.add.image(400, 300, 'background').setAlpha(0.2);

        this.add.text(400, 100, 'GAME OVER', {
            fontFamily: 'Arial Black', fontSize: '40px', color: '#ffb6c1',
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);

        const winnerColor = winner === 'Cat' ? '#c44343' : '#486bc5';

        this.add.text(400, 250, `Winner: ${winner}!`, {
            fontFamily: 'Arial Black', fontSize: '35px', color: winnerColor,
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);

        this.add.text(400, 320, `Score: ${s1} - ${s2}`, {
            fontFamily: 'Arial Black', fontSize: '35px', color: winnerColor,
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);

        const clickText = this.add.text(400, 450, 'Back to Main Menu', {
            fontFamily: 'Arial Black', fontSize: '50px', color: '#ffb6c1',
            stroke: '#000000', strokeThickness: 8,
            shadow: { offsetX: 3, offsetY: 3, color: '#000', blur: 5, stroke: true, fill: true }
        }).setOrigin(0.5);

        this.tweens.add({
            targets: clickText,
            scaleX: 1.1,
            scaleY: 1.1,
            duration: 800,
            yoyo: true,
            repeat: -1
        });

        this.input.once('pointerdown', () => {
            this.scene.start('MainMenu');
        });
    }
}