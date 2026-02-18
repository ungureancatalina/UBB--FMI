using UnityEngine;
using UnityEngine.Rendering;
using UnityEngine.Rendering.Universal;

public class AnxietyVisualEffects : MonoBehaviour
{
    public AnxietyManager anxietyManager;
    public Volume postProcessVolume;

   
    Vignette vignette;
    DepthOfField depthOfField;

    void Start()
    {
        postProcessVolume.profile.TryGet(out vignette);
        postProcessVolume.profile.TryGet(out depthOfField);
    }

    void Update()
    {
        float p = anxietyManager.anxiety / anxietyManager.maxAnxiety;
        ExposureLevel lvl = anxietyManager.exposureLevel;

        ApplyVignette(p, lvl);
        ApplyBlur(p, lvl);
    }

    void ApplyVignette(float p, ExposureLevel lvl)
    {
        if (vignette == null) return;

        float target = 0.1f; 

        if (lvl == ExposureLevel.Level2 && p > 0.66f)
            target = 0.3f;

        if (lvl == ExposureLevel.Level3)
        {
            if (p > 0.5f) target = 0.3f;
            if (p > 0.75f) target = 0.4f;
        }

        if (lvl == ExposureLevel.Level4)
        {
            if (p > 0.4f) target = 0.4f;
            if (p > 0.75f) target = 0.5f;
        }

        vignette.intensity.value = Mathf.Lerp(
            vignette.intensity.value,
            target,
            Time.deltaTime * 3f
        );
    }

    void ApplyBlur(float p, ExposureLevel lvl)
    {
        if (depthOfField == null) return;

        if (lvl == ExposureLevel.Level4 && p > 0.75f)
        {
            depthOfField.active = true;
            depthOfField.gaussianStart.value = 0.2f;
            depthOfField.gaussianEnd.value = 0.6f;
        }
        else
        {
            depthOfField.active = false;
        }
    }
}
