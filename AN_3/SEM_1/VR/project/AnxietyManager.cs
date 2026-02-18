using UnityEngine;
using UnityEngine.UI;

public enum ExposureLevel
{
    Level1,
    Level2,
    Level3,
    Level4
}


public class AnxietyManager : MonoBehaviour
{
    [Header("UI")]
    public Slider anxietySlider;
    public Image fillImage;

    [Header("Anxiety Values")]
    public float anxiety = 0f;
    public float maxAnxiety = 100f;

    [Header("Rates")]
    public float baseIncreaseSpeed = 7f;
    public float extraPerNPC = 3f;
    public float decreaseSpeed = 4f;

    [Header("Exposure Level")]
    public ExposureLevel exposureLevel = ExposureLevel.Level1;
    
    

    private float npcsLooking = 0f;

    public void NPCStartedLooking(float amount)
    {
        npcsLooking += amount;
    }

    public void NPCStoppedLooking(float amount)
    {
        npcsLooking -= amount;
        npcsLooking = Mathf.Max(0, npcsLooking);
    }


    void Start()
    {
        anxietySlider.minValue = 0;
        anxietySlider.maxValue = maxAnxiety;

        if (exposureLevel == ExposureLevel.Level4)
            anxiety = 40f;
        else
            anxiety = 0f;

        anxietySlider.value = anxiety;
        UpdateColor();
    }

    void Update()
    {
        if (npcsLooking > 0)
        {
            float increase =
                baseIncreaseSpeed +
                extraPerNPC * (npcsLooking - 1);

            anxiety += increase * Time.deltaTime;
        }
        else
        {
            anxiety -= decreaseSpeed * Time.deltaTime;
        }

        anxiety = Mathf.Clamp(anxiety, 0f, maxAnxiety);
        anxietySlider.value = anxiety;
        UpdateColor();
    }
    
    void UpdateColor()
    {
        float p = anxiety / maxAnxiety;

        switch (exposureLevel)
        {
            case ExposureLevel.Level1:
                if (p < 0.4f)
                    fillImage.color = Color.green;
                else
                    fillImage.color = Color.yellow;
                break;

            case ExposureLevel.Level2:
                if (p < 0.33f)
                    fillImage.color = Color.green;
                else if (p < 0.66f)
                    fillImage.color = Color.yellow;
                else
                    fillImage.color = new Color(1f, 0.5f, 0f);
                break;

            case ExposureLevel.Level3:
                if (p < 0.25f)
                    fillImage.color = Color.green;
                else if (p < 0.5f)
                    fillImage.color = Color.yellow;
                else if (p < 0.75f)
                    fillImage.color = new Color(1f, 0.5f, 0f);
                else
                    fillImage.color = Color.red;
                break;

            case ExposureLevel.Level4:
                if (p < 0.4f)
                    fillImage.color = Color.yellow;
                else if (p < 0.75f)
                    fillImage.color = new Color(1f, 0.5f, 0f);
                else
                    fillImage.color = Color.red;
                break;
        }
    }
}
