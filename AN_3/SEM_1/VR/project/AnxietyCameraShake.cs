using UnityEngine;

public class AnxietyCameraShake : MonoBehaviour
{
    public AnxietyManager anxietyManager;

    public float mildShake = 0.003f;
    public float mediumShake = 0.007f;
    public float strongShake = 0.015f;
    public float shakeSpeed = 20f;

    Vector3 startPos;

    void Start()
    {
        startPos = transform.localPosition;
    }

    void Update()
    {
        float p = anxietyManager.anxiety / anxietyManager.maxAnxiety;
        ExposureLevel lvl = anxietyManager.exposureLevel;

        float strength = 0f;

        if (lvl == ExposureLevel.Level3)
        {
            if (p > 0.5f) strength = mildShake;
            if (p > 0.75f) strength = mediumShake;
        }

        if (lvl == ExposureLevel.Level4)
        {
            if (p > 0.4f) strength = mediumShake;
            if (p > 0.75f) strength = strongShake;
        }

        float x = Mathf.PerlinNoise(Time.time * shakeSpeed, 0) - 0.5f;
        float y = Mathf.PerlinNoise(0, Time.time * shakeSpeed) - 0.5f;

        transform.localPosition = startPos + new Vector3(x, y, 0) * strength;
        
    }
}